package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.entity.Adherent;
import com.springjpa.entity.DureePret;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Profil;
import com.springjpa.entity.Reservation;
import com.springjpa.entity.RetourPret;
import com.springjpa.repository.ExemplaireRepository;

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
                dateFinPretOuRetour = pret.getDateFinPret();
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
