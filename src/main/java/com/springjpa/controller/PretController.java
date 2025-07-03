package com.springjpa.controller;

import com.springjpa.entity.Adherent;
import com.springjpa.entity.DureePret;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Livre;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Profil;
import com.springjpa.service.AdherentService;
import com.springjpa.service.AdminService;
import com.springjpa.service.ExemplaireService;
import com.springjpa.service.QuotaTypePretService;
import com.springjpa.service.LivreService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springjpa.service.DureePretService;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.PretService;
import com.springjpa.service.TypePretService;

@Controller
public class PretController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private QuotaTypePretService qutoTypePret;

    @Autowired
    private TypePretService typePretService;

    @Autowired
    private PretService pretService;

    @Autowired
    private DureePretService dureePretService;

    @Autowired
    private QuotaTypePretService quotaTypePretService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PenaliteService penaliteService;


    @GetMapping("/")
    public String index() {
        return "index"; // Redirection vers la page d'accueil
    }

    private void prepareModelPretPage(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
    }

    @GetMapping("/preter")
    public String preter(Model model) {

        prepareModelPretPage(model);

        return "pret";
    }

    @PostMapping("/preter")
    public String preterLivre(@RequestParam("matriculeAdherent") int matriculeAdherent,
                              @RequestParam("typePretId") int typePretId,
                              @RequestParam("livreId") int livreId, Model model) {

        Adherent adherent = adherentService.findByMatricule(matriculeAdherent);
        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = exemplaireService.findByLivreIdLivre(livre.getIdLivre());
        Exemplaire exemplaireOpt =  null;
    
        // 1. L'adhérant doit être dans la base de donnée
        if (adherent.getIdAdherent() == null) {
            model.addAttribute("error", "Adhérant inexistant.");
            prepareModelPretPage(model);
            return "pret";
        }

        // 2. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        boolean inscrit = adherentService.isInscrit(adherent.getIdAdherent());
        if (!inscrit) {
            model.addAttribute("error", "Adhérant non inscrit ou inscription inactive.");
            return "pret";
        }

        for (Exemplaire exemplaire : exemplaires) {
            // 3. Le numéro de l'exemplaire doit exister
            exemplaireOpt = exemplaireService.findById(exemplaire.getIdExemplaire());
            if (exemplaireOpt.getIdExemplaire() == null) {
                model.addAttribute("message", "Exemplaire n°" + exemplaire.getIdExemplaire() + " inexistant.");
                prepareModelPretPage(model);
                return "pret";
            }

            Profil profil = adherent.getProfil();
            DureePret dureePret = dureePretService.findByProfilIdProfil(profil.getIdProfil());

            // 4. L'exemplaire doit être disponible (pas déjà prêté ou déjà réservé)
            try {
                exemplaireService.isExemplaireDisponible(exemplaire.getIdExemplaire(), LocalDateTime.now(), LocalDateTime.now().plusDays(dureePret.getDuree()));
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                prepareModelPretPage(model);
                return "pret";
            }
        }

        // 5. Vérifier si l'adhérant n'est pas pénalisé
        boolean penalise = penaliteService.isPenalise(LocalDateTime.now(),adherent.getIdAdherent()); 
        if (penalise) {
            model.addAttribute("message", "Adhérant pénalisé, prêt impossible.");
            prepareModelPretPage(model);
            return "pret";
        }

        // 6. Vérifier que l'adhérant ne dépasse pas le quota pour le type de prêt
        boolean depasseQuota = quotaTypePretService.depassementQuota(adherent.getIdAdherent(),typePretId); 

        if (depasseQuota) {
            model.addAttribute("message", "Quota de prêt dépassé." + depasseQuota);
            prepareModelPretPage(model);
            return "pret";
        }

        // 7. L'adhérant peut-il prêter ce livre (ex: restrictions sur certains livres)
        Boolean peutPreter = livre.peutPreter(adherent.getProfil());

        if (!peutPreter) {
            model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre a cause de votre age ou du type de votre profil");
            prepareModelPretPage(model);
            return "pret";
        }


        Pret pret = new Pret(
            LocalDateTime.now(), // Date de début du prêt
            adminService.findById(1), // Admin (à définir selon votre logique, peut-être l'admin connecté)
            typePretService.findById(typePretId), // Type de prêt
            exemplaireOpt, // Exemplaire (le dernier exemplaire vérifié)
            adherent // Adhérant
        );
        

        if (exemplaireOpt != null) {
            pretService.save(pret);
            model.addAttribute("message", "Prêt validé et inséré");
        }

        prepareModelPretPage(model);
        
        
        // Redirection vers la page de confirmation ou d'accueil après le prêt
        return "pret";
        
    }
}