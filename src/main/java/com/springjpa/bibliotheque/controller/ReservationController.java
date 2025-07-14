package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.springjpa.bibliotheque.entity.DureeReservation;
import com.springjpa.bibliotheque.entity.Exemplaire;
import com.springjpa.bibliotheque.entity.Livre;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.entity.Reservation;
import com.springjpa.bibliotheque.entity.StatutReservation;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.DureeReservationService;
import com.springjpa.bibliotheque.service.ExemplaireService;
import com.springjpa.bibliotheque.service.LivreService;
import com.springjpa.bibliotheque.service.PenaliteService;
import com.springjpa.bibliotheque.service.ReservationService;
import com.springjpa.bibliotheque.service.StatutReservationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/adherent/reservation")
public class ReservationController {
    
    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private DureeReservationService dureeReservationService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StatutReservationService statutReservationService;
    
    private void prepareModelPage(Model model, Adherent adherent) {
        List<Reservation> adherentReservations = reservationService.findByAdherentMatricule(adherent.getMatricule());
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("reservations", adherentReservations);
    }

    @GetMapping("")
    public String reserver(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }
        prepareModelPage(model,adherent);
        model.addAttribute("adherent", adherent);
        return "adherent/reservation";
    }

    @PostMapping("")
    public String resereverLivre(
                            @RequestParam("livreId") int livreId, 
                            @RequestParam("dateReservation") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateReservation,
                            HttpSession session, RedirectAttributes redirectAttributes, Model model) {

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        // LocalDateTime dateReservation = LocalDateTime.parse(dateReservationString, formatter);
        
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }                             

        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = exemplaireService.findByLivreIdLivre(livre.getIdLivre());
        Exemplaire exemplaireReserve = null;

        // 0. Vérifier que la date de réservation n'est pas dans le passé
        if (dateReservation.isBefore(LocalDateTime.now())) {
            prepareModelPage(model,adherent);
            model.addAttribute("message", "La date de réservation ne peut pas être dans le passé");
            return "/adherent/reservation";
        }

        // 1. Vérifier que l'adhérent est inscrit
        boolean inscrit = adherentService.isInscrit(adherent.getMatricule());
        if (!inscrit) {
            prepareModelPage(model,adherent);
            model.addAttribute("message", "Adhérant non inscrit ou inscription inactive.");
            return "/adherent/reservation";
        }

        Profil profil = adherent.getProfil();
        DureeReservation dureeReservation = dureeReservationService.findDureeReservationByProfilIdProfil(profil.getIdProfil());
        for (Exemplaire exemplaire : exemplaires) {
            boolean disponibilite = exemplaireService.isExemplaireDisponible(exemplaire, LocalDateTime.now(), LocalDateTime.now().plusDays(dureeReservation.getDuree()));
            if(disponibilite) {
                exemplaireReserve = exemplaire;
            }
        }

        // 2. Vérifier la disponibilité de l'exemplaire
        if(exemplaireReserve==null) {
            model.addAttribute("message", "Aucun exemplaire de ce livre n'est disponible");
            prepareModelPage(model,adherent);
            return "/adherent/reservation";
        }

        // 3. Vérifier si l'adhérent n'est pas pénalisé
        boolean penalise = penaliteService.isPenalise(LocalDateTime.now(),adherent.getIdAdherent()); 
        if (penalise) {
            model.addAttribute("message", "Adhérant pénalisé, réservation impossible.");
            prepareModelPage(model,adherent);
            return "/adherent/reservation";
        }
        
        // 4. Vérifier le quota de réservations
        boolean depasseQuota = reservationService.deppassementQuotaReservation(adherent);
        if (depasseQuota) {
            model.addAttribute("message", "Quota de réservation dépassé.");
            prepareModelPage(model,adherent);
            return "/adherent/reservation";
        }
        
        // 5. L'adhérant peut-il prêter ce livre (ex: restrictions sur certains livres)
        Boolean peutPreter = livre.peutAcceder(profil);
        if (!peutPreter) {
            prepareModelPage(model, adherent);
            String test = "";
            for(Profil prof: livre.getProfils()) {
                test = test.concat(prof.getNomProfil());
            }
            model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre à cause des restrictions de votre profil | " + test + "| " + adherent.getProfil().getNomProfil());
            return "/adherent/reservation";
        }

        LocalDateTime dateExpirationReservation = reservationService.getDateExpirationReservation(adherent,dateReservation);
        StatutReservation statutReservation = statutReservationService.findById(1); // Statut "En attente"

        Reservation reservation = new Reservation(dateReservation, 
                                                dateExpirationReservation, 
                                                statutReservation, 
                                                exemplaireReserve, 
                                                adherent);
        
        if(exemplaireReserve != null) {
            reservationService.save(reservation);
            model.addAttribute("message", "Réservation faite pour le livre : " + livre.getTitre() + " \npour le : " + dateReservation + " \nexpire le : " + dateExpirationReservation);
        }

        prepareModelPage(model,adherent);
        return "/adherent/reservation";
    }

    @PostMapping("/annuler")
    public String annulerReservation(@RequestParam("idReservation") Integer idReservation, 
                                    HttpSession session, 
                                    RedirectAttributes redirectAttributes) {
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent == null) {
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }
        
        try {
            Reservation reservation = reservationService.findById(idReservation);
            if(reservation == null || !reservation.getAdherent().getIdAdherent().equals(adherent.getIdAdherent())) {
                redirectAttributes.addAttribute("message", "Réservation introuvable ou non autorisée");
                return "redirect:/adherent/reservation";
            }
            
            // Vérifier que la réservation est bien en attente (statut = 1)
            if(reservation.getStatut().getIdStatut() != 1) {
                redirectAttributes.addAttribute("message", "Seules les réservations en attente peuvent être annulées");
                return "redirect:/adherent/reservation";
            }
            
            // Mettre à jour le statut à "Annulée" (4)
            StatutReservation statutAnnule = statutReservationService.findById(4);
            reservation.setStatut(statutAnnule);
            reservationService.save(reservation);
            
            redirectAttributes.addAttribute("message", "Réservation annulée avec succès");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", "Erreur lors de l'annulation de la réservation");
        }
        
        return "redirect:/adherent/reservation";
    }
}