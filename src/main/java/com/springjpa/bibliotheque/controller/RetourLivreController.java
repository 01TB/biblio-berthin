package com.springjpa.bibliotheque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Admin;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.DureePretService;
import com.springjpa.bibliotheque.service.ExemplaireService;
import com.springjpa.bibliotheque.service.LivreService;
import com.springjpa.bibliotheque.service.PenaliteService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.QuotaTypePretService;
import com.springjpa.bibliotheque.service.TypePretService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/retour")
public class RetourLivreController {

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
    private PenaliteService penaliteService;


    private void prepareModelPage(Model model, Adherent adherent) {
        model.addAttribute("adherent", adherent);
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
    }

    @GetMapping("")
    public String retourner(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        model.addAttribute("admin",admin);
        return "admin/retour";
    }

    @PostMapping("")
    public String rechercherPrets(
                            @RequestParam("matriculeAdherent") int matriculeAdherent,
                            HttpSession session, RedirectAttributes redirectAttributes, Model model) {

        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin",admin);
        if(admin==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        Adherent adherent = adherentService.findByMatricule(matriculeAdherent);
        List<Pret> pretsAdherent = pretService.findByAdherentIdAdherent(adherent.getIdAdherent());
        
        // 1. L'adhérant doit être dans la base de donnée
        if (adherent == null) {
            model.addAttribute("message", "Adhérant inexistant.");
            return "/admin/retour";
        }
        
        // 2. Si l'adhérent n'a aucun prêt
        if( pretsAdherent == null | pretsAdherent.isEmpty() ) {
            model.addAttribute("message", "L'adhérent " + adherent.getMatricule() + " n'a aucun prêt en cours.");
            return "/admin/retour";
        }
        
        prepareModelPage(model,adherent);
        model.addAttribute("pretsAdherent",pretsAdherent);
        
        return "/admin/retour";
    }

    @PostMapping("/retourner")
    public String retournerExemplaire(
                                    @RequestParam("idPret") int idPret,
                                    
                                    ){

    }
}
