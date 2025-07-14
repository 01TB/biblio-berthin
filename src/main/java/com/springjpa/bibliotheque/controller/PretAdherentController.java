package com.springjpa.bibliotheque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.ProlongationPret;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.LivreService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.ProlongationPretService;
import com.springjpa.bibliotheque.service.TypePretService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/adherent/pret")
public class PretAdherentController {

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private TypePretService typePretService;

    @Autowired
    private PretService pretService;

    @Autowired
    private ProlongationPretService prolongationPretService;

    private void prepareModelPage(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
    }

    @GetMapping("")
    public String pretsEnCours(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }
        
        List<Pret> pretsAdherent = pretService.findByAdherentIdAdherent(adherent.getIdAdherent());
        
        // Récupérer les prolongations pour chaque prêt
        List<ProlongationPret> prolongations = pretsAdherent.stream()
            .flatMap(pret -> prolongationPretService.findByPretIdPret(pret.getIdPret()).stream())
            .collect(Collectors.toList());
        
        prepareModelPage(model);
        model.addAttribute("adherent", adherent);
        model.addAttribute("pretsAdherent", pretsAdherent);
        model.addAttribute("prolongations", prolongations);
        return "adherent/pret";
    }
}