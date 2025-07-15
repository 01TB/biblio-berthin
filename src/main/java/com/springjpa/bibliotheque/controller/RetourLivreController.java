package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Admin;
import com.springjpa.bibliotheque.entity.Penalite;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.RetourPret;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.PenaliteService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.RetourPretService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/retour")
public class RetourLivreController {

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private PretService pretService;

    @Autowired
    private RetourPretService retourPretService;

    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("")
    public String retourner(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Veuillez vous connecter en tant qu'administrateur");
            return "redirect:/";
        }

        model.addAttribute("admin", admin);
        return "admin/retour";
    }

    @PostMapping("/rechercher")
    public String rechercherPrets(
            @RequestParam("matriculeAdherent") Integer matriculeAdherent,
            HttpSession session, RedirectAttributes redirectAttributes, Model model) {

        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Session expirée, veuillez vous reconnecter");
            return "redirect:/";
        }

        model.addAttribute("admin", admin);

        // 1. Vérifier que l'adhérent existe
        Adherent adherent = adherentService.findByMatricule(matriculeAdherent);
        if (adherent == null) {
            model.addAttribute("error", "Aucun adhérent trouvé avec le matricule: " + matriculeAdherent);
            return "admin/retour";
        }

        // 2. Récupérer les prêts en cours de l'adhérent
        List<Pret> pretsAdherent = pretService.findByAdherentIdAdherent(adherent.getIdAdherent())
                .stream()
                .filter(pret -> !retourPretService.existsByPretIdPret(pret.getIdPret()))
                .collect(Collectors.toList());

        if(pretsAdherent.isEmpty()) {
            model.addAttribute("error", "L'adhérent n'a aucun prêt en cours.");
            return "admin/retour";
        }

        model.addAttribute("adherent", adherent);
        model.addAttribute("pretsAdherent", pretsAdherent);
        model.addAttribute("pretService", pretService);
        
        return "admin/retour";
    }

    @PostMapping("/retourner")
    public String retournerLivre(
            @RequestParam("idPret") Integer idPret,
            @RequestParam("dateRetour") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateRetour,
            HttpSession session, 
            RedirectAttributes redirectAttributes, 
            Model model) {
        
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        
        // 0. Vérifier que le prêt existe
        Pret pret = pretService.findById(idPret);
        if(pret == null) {
            redirectAttributes.addFlashAttribute("error", "Prêt introuvable");
            return "redirect:/admin/retour";
        }

        // 1. Vérifier que la date de retour n'est pas avant la date de prêt
        if (dateRetour.isBefore(pret.getDateDebut())) {
            model.addAttribute("message", "La date de retour ne peut pas être avant la date de prêt");
            return "/adherent/retour";
        }

        // 2. Vérifier si le prêt n'a pas déjà été retourné
        if(retourPretService.existsByPretIdPret(idPret)) {
            redirectAttributes.addFlashAttribute("error", "Ce prêt a déjà été retourné");
            return "redirect:/admin/retour";
        }

        // 3. Enregistrer le retour
        RetourPret retourPret = new RetourPret(dateRetour, pret);
        retourPretService.save(retourPret);

        // 4. Vérifier si le retour est en retard et appliquer une pénalité si nécessaire
        LocalDateTime dateFinPret = pretService.getDateFinPret(pret);
        if(dateRetour.isAfter(dateFinPret)) {
            Penalite penalite = new Penalite(dateRetour, pret.getAdherent());
            penaliteService.save(penalite);
            redirectAttributes.addFlashAttribute("warning", 
                "Pénalité appliquée pour retard de " + 
                dateRetour.toLocalDate().compareTo(dateFinPret.toLocalDate()) + " jours");
        }

        redirectAttributes.addFlashAttribute("success", 
            "Livre " + pret.getExemplaire().getLivre().getTitre() + 
            " retourné avec succès");
        
        return "redirect:/admin/retour";
    }
}