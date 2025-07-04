package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.DureePret;
import com.springjpa.bibliotheque.entity.Exemplaire;
import com.springjpa.bibliotheque.entity.Livre;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.AdminService;
import com.springjpa.bibliotheque.service.DureePretService;
import com.springjpa.bibliotheque.service.ExemplaireService;
import com.springjpa.bibliotheque.service.LivreService;
import com.springjpa.bibliotheque.service.PenaliteService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.QuotaTypePretService;
import com.springjpa.bibliotheque.service.TypePretService;

@Controller
public class PretController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherentService adherentService;

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


    @GetMapping("/pret")
    public String index() {
        return "home"; // Redirection vers la page d'accueil
    }

    private void prepareModelPretPage(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
    }

    @GetMapping("/pret/preter")
    public String preter(Model model) {

        prepareModelPretPage(model);

        return "admin/pret";
    }

    @PostMapping("/pret/preter")
    public String preterLivre(@RequestParam("matriculeAdherent") int matriculeAdherent,
                              @RequestParam("typePretId") int typePretId,
                              @RequestParam("livreId") int livreId, Model model) {

        Adherent adherent = adherentService.findByMatricule(matriculeAdherent);
        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = exemplaireService.findByLivreIdLivre(livre.getIdLivre());
        Exemplaire exemplaireOpt =  null;
    
        // 1. L'adhérant doit être dans la base de donnée
        if (adherent == null) {
            model.addAttribute("message", "Adhérant inexistant.");
            prepareModelPretPage(model);
            return "admin/pret";
        }

        // 2. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        boolean inscrit = adherentService.isInscrit(matriculeAdherent);
        if (!inscrit) {
            prepareModelPretPage(model);
            model.addAttribute("message", "Adhérant non inscrit ou inscription inactive.");
            return "admin/pret";
        }

        for (Exemplaire exemplaire : exemplaires) {
            // 3. Le numéro de l'exemplaire doit exister
            exemplaireOpt = exemplaireService.findById(exemplaire.getIdExemplaire());
            if (exemplaireOpt.getIdExemplaire() == null) {
                model.addAttribute("message", "Exemplaire n°" + exemplaire.getIdExemplaire() + " inexistant.");
                prepareModelPretPage(model);
                return "admin/pret";
            }

            Profil profil = adherent.getProfil();
            DureePret dureePret = dureePretService.findByProfilIdProfil(profil.getIdProfil());

            // 4. L'exemplaire doit être disponible (pas déjà prêté ou déjà réservé)
            try {
                exemplaireService.isExemplaireDisponible(exemplaire.getIdExemplaire(), LocalDateTime.now(), LocalDateTime.now().plusDays(dureePret.getDuree()));
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                prepareModelPretPage(model);
                return "admin/pret";
            }
        }

        // 5. Vérifier si l'adhérant n'est pas pénalisé
        boolean penalise = penaliteService.isPenalise(LocalDateTime.now(),adherent.getIdAdherent()); 
        if (penalise) {
            model.addAttribute("message", "Adhérant pénalisé, prêt impossible.");
            prepareModelPretPage(model);
            return "admin/pret";
        }

        // 6. Vérifier que l'adhérant ne dépasse pas le quota pour le type de prêt
        boolean depasseQuota = quotaTypePretService.depassementQuota(adherent.getIdAdherent(),typePretId); 

        if (depasseQuota) {
            model.addAttribute("message", "Quota de prêt dépassé." + depasseQuota);
            prepareModelPretPage(model);
            return "admin/pret";
        }

        // 7. L'adhérant peut-il prêter ce livre (ex: restrictions sur certains livres)
        Boolean peutPreter = livre.peutPreter(adherent.getProfil());

        if (!peutPreter) {
            model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre a cause de votre age ou du type de votre profil");
            prepareModelPretPage(model);
            return "admin/pret";
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
            prepareModelPretPage(model);
            model.addAttribute("message", "Prêt validé et inséré");
        }

        prepareModelPretPage(model);
        
        
        // Redirection vers la page de confirmation ou d'accueil après le prêt
        return "admin/pret";
        
    }
}