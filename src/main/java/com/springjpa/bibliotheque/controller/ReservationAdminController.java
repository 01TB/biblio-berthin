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
import com.springjpa.bibliotheque.entity.Reservation;
import com.springjpa.bibliotheque.entity.StatutReservation;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.ReservationService;
import com.springjpa.bibliotheque.service.StatutReservationService;
import com.springjpa.bibliotheque.service.TypePretService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/reservation")
public class ReservationAdminController {

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StatutReservationService statutReservationService;

    @Autowired
    private TypePretService typePretService;
    
    @GetMapping("")
    public String retourner(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Veuillez vous connecter en tant qu'administrateur");
            return "redirect:/";
        }

        model.addAttribute("admin", admin);
        return "admin/reservation";
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
            return "admin/reservation";
        }

        // 2. Récupérer les réservations en cours de l'adhérent
        List<Reservation> reservationsAdherent = reservationService.findByAdherentMatricule(adherent.getMatricule());

        if(reservationsAdherent.isEmpty()) {
            model.addAttribute("error", "L'adhérent n'a aucune réservation en cours.");
            return "admin/reservation";
        }

        List<StatutReservation> allStatutReservation = statutReservationService.findAll();
        
        model.addAttribute("adherent", adherent);
        model.addAttribute("reservationsAdherent", reservationsAdherent);
        model.addAttribute("allStatutReservation", allStatutReservation);
        model.addAttribute("reservationService", reservationService);
        model.addAttribute("typesPret", typePretService.findAll());
        
        return "admin/reservation";
    }

    @PostMapping("/reserver")
    public String reserverLivre(
                            @RequestParam("idReservation") Integer idReservation,
                            @RequestParam("idStatut") Integer idStatut,
                            HttpSession session, 
                            RedirectAttributes redirectAttributes, 
                            Model model) {

        Admin admin = (Admin)session.getAttribute("admin");
        if(admin == null){
            redirectAttributes.addFlashAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        // 0. Vérifier que la réservation existe
        Reservation reservation = reservationService.findById(idReservation);
        if(reservation == null) {
            redirectAttributes.addFlashAttribute("error", "Prêt introuvable");
            return "redirect:/admin/reservation";
        }

        // 1. Vérifier que le statut existe
        StatutReservation statutReservation = statutReservationService.findById(idStatut);
        if(statutReservation == null) {
            redirectAttributes.addFlashAttribute("error", "Statut de réservation introuvable");
            return "redirect:/admin/reservation";
        }

        try {
            // Mettre à jour le statut de la réservation
            reservation.setStatut(statutReservation);
            reservationService.save(reservation);
            
            redirectAttributes.addAttribute("message", "Statut de la réservation N°" 
                                            + reservation.getIdReservation() 
                                            + " modifié.");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", "Erreur lors de la modification du status de la réservation");
        }

        Adherent adherent = reservation.getAdherent();
        List<Reservation> reservationsAdherent = reservationService.findByAdherentMatricule(adherent.getMatricule());

        if(reservationsAdherent.isEmpty()) {
            model.addAttribute("error", "L'adhérent n'a aucune réservation en cours.");
        }

        List<StatutReservation> allStatutReservation = statutReservationService.findAll();
        
        model.addAttribute("adherent", adherent);
        model.addAttribute("reservationsAdherent", reservationsAdherent);
        model.addAttribute("allStatutReservation", allStatutReservation);
        model.addAttribute("reservationService", reservationService);
        model.addAttribute("typesPret", typePretService.findAll());

        return "admin/reservation";
    }

}
