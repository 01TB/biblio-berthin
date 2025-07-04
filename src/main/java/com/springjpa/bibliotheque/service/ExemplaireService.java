package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.DureePret;
import com.springjpa.bibliotheque.entity.Exemplaire;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Profil;
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

    public void isExemplaireDisponible(Integer idExemplaire, LocalDateTime dateDebut, LocalDateTime dateFin) throws Exception{

        // Check des prêts
        List<Pret> prets = pretService.findByExemplaireIdExemplaire(idExemplaire);
    
        for (Pret pret : prets) {
            // Adherent adherent = pret.getAdherent();
            // Profil profilAdherent = adherent.getProfil();
            // DureePret dureePretAdherent = dureePretService.findByProfilIdProfil(profilAdherent.getIdProfil());

            LocalDateTime dateDebutPret = pret.getDateDebut();
            LocalDateTime dateFinPretOuRetour = null;
    

            RetourPret retour = retourPretService.findByPretIdPret(pret.getIdPret());
            if (retour != null) {
                dateFinPretOuRetour = retour.getDateRetour();
            } else {
                dateFinPretOuRetour = pretService.getDateFinPret(pret);
            }
        
            if (PretService.datesSeChevauchent(dateDebut, dateFin, dateDebutPret, dateFinPretOuRetour)) {
                throw new Exception("Exemplaire n°" + idExemplaire + " encore prêté.");
            }
        }
        
        // Check des réservations
        List<Reservation> reservations = reservationService.findByExemplaireIdExemplaire(idExemplaire);

        for (Reservation reservation: reservations) {
            if (PretService.datesSeChevauchent(dateDebut, dateFin, reservation.getDateReservation(), reservation.getDateExpiration())) {
                throw new Exception("Exemplaire n°" + idExemplaire + " déjà réservé.");
            }
        }
    }
}
