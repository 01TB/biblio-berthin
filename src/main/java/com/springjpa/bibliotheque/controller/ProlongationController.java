package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
import com.springjpa.bibliotheque.entity.DureePret;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.entity.ProlongationPret;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.DureePretService;
import com.springjpa.bibliotheque.service.ExemplaireService;
import com.springjpa.bibliotheque.service.PenaliteService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.ProlongationPretService;
import com.springjpa.bibliotheque.service.RetourPretService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/prolongation")
public class ProlongationController {

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private PretService pretService;

    @Autowired
    private RetourPretService retourPretService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private ProlongationPretService prolongationPretService;

    @Autowired
    private DureePretService dureePretService;

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("")
    public String retourner(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Veuillez vous connecter en tant qu'administrateur");
            return "redirect:/";
        }

        model.addAttribute("admin", admin);
        return "admin/prolongation";
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
            return "admin/prolongation";
        }

        // 2. Récupérer les prêts en cours de l'adhérent
        List<Pret> pretsAdherent = pretService.findByAdherentIdAdherent(adherent.getIdAdherent())
                .stream()
                .filter(pret -> !retourPretService.existsByPretIdPret(pret.getIdPret()))
                .collect(Collectors.toList());

        if(pretsAdherent.isEmpty()) {
            model.addAttribute("error", "L'adhérent n'a aucun prêt en cours.");
            return "admin/prolongation";
        }

        model.addAttribute("adherent", adherent);
        model.addAttribute("pretsAdherent", pretsAdherent);
        model.addAttribute("pretService", pretService);
        
        return "admin/prolongation";
    }

    @PostMapping("/prolonger")
    public String prolongerPret(
            @RequestParam("matriculeAdherent") int matriculeAdherent,
            @RequestParam("idPret") Integer idPret,
            HttpSession session, 
            RedirectAttributes redirectAttributes, 
            Model model) {

        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        Adherent adherent = adherentService.findByMatricule(matriculeAdherent);
        // 0. L'adhérant doit être dans la base de donnée
        if (adherent == null) {
            redirectAttributes.addAttribute("message", "Adhérant inexistant.");
            return "redirect:admin/prolongation";
        }

        // 1. Vérifier que le prêt existe
        Pret pret = pretService.findById(idPret);
        if(pret == null) {
            redirectAttributes.addFlashAttribute("error", "Prêt introuvable");
            return "redirect:/admin/prolongation";
        }

        // Date de début de la prolongation
        LocalDateTime dateProlongation = pretService.getDateFinPret(pret);

        // // 1. Vérifier que la date de prolongation n'est pas avant la date de prêt
        // if (dateProlongation.isBefore(pret.getDateDebut())) {
        //     redirectAttributes.addAttribute("message", "La date de prolongation ne peut pas être avant la date de prêt");
        //     return "redirect:/adherent/prolongation";
        // }

        // 2. Vérifier si le prêt n'a pas déjà été retourné
        if(retourPretService.existsByPretIdPret(idPret)) {
            redirectAttributes.addFlashAttribute("error", "Ce prêt a déjà été retourné");
            return "redirect:/admin/prolongation";
        }

        // 4. Vérifier si l'adhérant n'est pas pénalisé
        boolean penalise = penaliteService.isPenalise(LocalDateTime.now(),adherent.getIdAdherent()); 
        if (penalise) {
            redirectAttributes.addAttribute("message", "Adhérant pénalisé, prêt impossible.");
            return "redirect:admin/prolongation";
        }

        // 5. Vérifier que l'adhérant ne dépasse pas le quota prolongation
        Profil profil = adherent.getProfil();
        if(prolongationPretService.compteProlongationEnCours(adherent.getIdAdherent(),dateProlongation) >= profil.getQuotaProlongation()) {
            redirectAttributes.addAttribute("message", "Adhérant ayant atteint le quota de prolongation.");
            return "redirect:admin/prolongation";
        }
        
        DureePret dureePret = dureePretService.findByProfilIdProfil(profil.getIdProfil());
        
        LocalDateTime dateFinProlongation = dateProlongation.plusDays(dureePret.getDuree());
        
        // 6. Exemplaire non réserver ou prêter à cette date
        if(exemplaireService.isExemplaireDisponible(pret.getExemplaire(), adherent, dateProlongation, dateFinProlongation)) {
            redirectAttributes.addAttribute("message", "Exemplaire réserver ou prêter à cette date.");
            return "redirect:admin/prolongation";
        }

        // 7. Enregistrer le prolongement
        ProlongationPret prolongationPret = new ProlongationPret(dateProlongation, pret);
        prolongationPretService.save(prolongationPret);


        redirectAttributes.addFlashAttribute("Prolongation à partir du " + dateProlongation + " jusqu'au " + dateFinProlongation);
        
        return "redirect:/admin/prolongation";        
    }
}
