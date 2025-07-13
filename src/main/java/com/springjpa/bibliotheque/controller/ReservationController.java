package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
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
    
    private void prepareModelPage(Model model) {
        model.addAttribute("livres", livreService.findAll());
    }

    @GetMapping("")
    public String reserver(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }
        prepareModelPage(model);
        model.addAttribute("adherent", adherent);
        return "adherent/reservation";
    }

    @PostMapping("")
   public String resereverLivre(
                            @RequestParam("livreId") int livreId, 
                            @RequestParam("dateReservation") LocalDateTime dateReservation,
                            HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent==null){
            redirectAttributes.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }                             

        Livre livre = livreService.findById(livreId);
        List<Exemplaire> exemplaires = exemplaireService.findByLivreIdLivre(livre.getIdLivre());
        Exemplaire exemplaireReserve = null;

        // 1. L'adhérant doit être inscrit (à adapter selon ta logique d'inscription)
        boolean inscrit = adherentService.isInscrit(adherent.getMatricule());
        if (!inscrit) {
            prepareModelPage(model);
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

        // 2. L'exemplaire doit être disponible (pas déjà prêté ou déjà réservé)
        if(exemplaireReserve==null) {
            model.addAttribute("message", "Aucun exemplaire de ce livre n'est disponible");
            prepareModelPage(model);
            return "/adherent/reservation";
        }

        // 3. Vérifier si l'adhérant n'est pas pénalisé
        boolean penalise = penaliteService.isPenalise(LocalDateTime.now(),adherent.getIdAdherent()); 
        if (penalise) {
            model.addAttribute("message", "Adhérant pénalisé, réservation impossible.");
            prepareModelPage(model);
            return "/adherent/reservation";
        }
        
        // 4. Vérifier que l'adhérant ne dépasse pas le quota pour les réservations
        boolean depasseQuota = reservationService.deppassementQuotaReservation(adherent);
        if (depasseQuota) {
            model.addAttribute("message", "Quota de réservation dépassé." + depasseQuota);
            prepareModelPage(model);
            return "/adherent/reservation";
        }
        
        // 6. L'adhérant peut-il prêter ce livre (ex: restrictions sur certains livres)
        Boolean peutPreter = livre.peutAcceder(adherent.getProfil());
        
        if (!peutPreter) {
            model.addAttribute("message", "Vous ne pouvez pas emprunter ce livre a cause de votre age ou du type de votre profil");
            prepareModelPage(model);
            return "/adherent/reservation";
        }

        LocalDateTime dateExpirationReservation = reservationService.getDateExpirationReservation(adherent,dateReservation);
        StatutReservation statutReservation = statutReservationService.findById(1);


        
        Reservation reservation = new Reservation(dateReservation, 
                                                  dateExpirationReservation, 
                                                  statutReservation, 
                                                  exemplaireReserve, 
                                                  adherent);
        
        if(exemplaireReserve != null) {
            reservationService.save(reservation);
            model.addAttribute("message", "Réservation faite pour le livre : " + livre.getTitre() + " \npour le : " + dateReservation + " \nexpire le : " + dateExpirationReservation);
        }

        prepareModelPage(model);
        return "/adherent/reservation";
    }


}
