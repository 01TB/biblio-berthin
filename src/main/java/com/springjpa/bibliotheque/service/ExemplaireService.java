package com.springjpa.bibliotheque.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.Exemplaire;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Reservation;
import com.springjpa.bibliotheque.entity.RetourPret;
import com.springjpa.bibliotheque.repository.ExemplaireRepository;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretService pretService;

    @Autowired
    private RetourPretService retourPretService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private DureePretService dureePretService;

    public Exemplaire findById(Integer id){
        return exemplaireRepository.findById(id).get();
    }

    public List<Exemplaire> findAll(){
        return exemplaireRepository.findAll();
    }

    public void save(Exemplaire exemplaire){
        exemplaireRepository.save(exemplaire);
    }

    public List<Exemplaire> findByLivreIdLivre(Integer idLivre){
        return exemplaireRepository.findByLivreIdLivre(idLivre);
    };

    public boolean isExemplairePrete(Exemplaire exemplaire, LocalDateTime dateDebut, LocalDateTime dateFin) {
        
        // Check des prêts
        List<Pret> prets = pretService.findByExemplaireIdExemplaire(exemplaire.getIdExemplaire());
    
        for (Pret pret : prets) {
            // Adherent adherent = pret.getAdherent();
            // Profil profilAdherent = adherent.getProfil();
            // DureePret dureePretAdherent = dureePretService.findByProfilIdProfil(profilAdherent.getIdProfil());

            LocalDateTime dateDebutPret = pret.getDateDebut();
            LocalDateTime dateFinPretOuRetour;
    

            RetourPret retour = retourPretService.findByPretIdPret(pret.getIdPret());
            if (retour != null) {
                dateFinPretOuRetour = retour.getDateRetour();
            } else {
                dateFinPretOuRetour = pretService.getDateFinPret(pret);
            }
        
            if (PretService.datesSeChevauchent(dateDebut, dateFin, dateDebutPret, dateFinPretOuRetour)) {
                return false;
            }
        }
        return true;
    }

    public boolean isExemplaireReserve(Exemplaire exemplaire, LocalDateTime dateDebut, LocalDateTime dateFin) {
        // Check des réservations
        List<Reservation> reservations = reservationService.findByExemplaireIdExemplaire(exemplaire.getIdExemplaire());

        for (Reservation reservation: reservations) {
            if (PretService.datesSeChevauchent(dateDebut, dateFin, reservation.getDateReservation(), reservation.getDateExpiration())) {
                return false;
            }
        }
        return true;
    }

    public boolean isExemplaireDisponible(Exemplaire exemplaire, LocalDateTime dateDebut, LocalDateTime dateFin) {

        // Check des prêts
        if(!isExemplairePrete(exemplaire,dateDebut,dateFin)) {
            return false;
        }
        
        // Check des réservations
        if(!isExemplaireReserve(exemplaire,dateDebut,dateFin)) {
            return false;
        }

        return true;
    }

    public boolean isOneExemplairesDisponible(List<Exemplaire> exemplaires, LocalDateTime dateDebut, LocalDateTime dateFin) throws Exception {

        // Check si au moins un exemplaire est disponible
        for(Exemplaire exemplaire : exemplaires) {
            if(isExemplaireDisponible(exemplaire,dateDebut,dateFin)) {
                return true;
            }
        }
        return false;
    }
}
