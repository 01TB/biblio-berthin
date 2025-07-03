package com.springjpa.controller;

import com.springjpa.entity.Adherent;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Livre;
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
    private AdherentService adherantService;

    @Autowired
    private QuotaTypePretService qutoTypePret;

    @Autowired
    private TypePretService typePretService;

    @Autowired
    private PretService pretService;

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
        model.addAttribute("adherants", adherantService.findAll());
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

        Adherent adherant = adherantService.findByMatricule(matriculeAdherent);
        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = exemplaireService.findByLivreIdLivre(livre.getIdLivre());
        Exemplaire exemplaireOpt =  null;
    
        // 1. L'adhérant doit être dans la base de donnée
        if (adherant.getIdAdherent() == null) {
            model.addAttribute("error", "Adhérant inexistant.");
            prepareModelPretPage(model);
            return "pret";
        }

        // 2. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        boolean inscrit = adherantService.isInscrit(adherant.getIdAdherent());
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

            // 4. L'exemplaire doit être disponible (pas déjà prêté)
            Boolean disponible = exemplaireService.isExemplaireDisponible(exemplaire.getIdExemplaire(), LocalDateTime.now(), UtilService.toDateTimeWithCurrentTime(dateFin));
            if (!disponible) {
                model.addAttribute("message", "Exemplaire n°" + exemplaire.getIdExemplaire() + " non disponible.");
                preparePretPage(model);
                return "pret";
            }
        }
        // Redirection vers la page de confirmation ou d'accueil après le prêt
        return "redirect:/";
        
    }
}