package com.springjpa.bibliotheque.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.DureeReservation;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.entity.Reservation;
import com.springjpa.bibliotheque.repository.DureeReservationRepository;
import com.springjpa.bibliotheque.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DureeReservationRepository dureeReservationRepository;

    public Reservation findById(Integer id){
        return reservationRepository.findById(id).get();
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public List<Reservation> findByExemplaireIdExemplaire(Integer idExemplaire){
        return reservationRepository.findByExemplaireIdExemplaire(idExemplaire);
    }
    
    public List<Reservation> findByAdherentMatricule(Integer matriculeAdherent){
        return reservationRepository.findByAdherentMatricule(matriculeAdherent);
    }

    public LocalDateTime getDateExpirationReservation(Adherent adherent, LocalDateTime dateReservation) {
        DureeReservation dureeReservation = dureeReservationRepository
                                            .findDureeReservationByProfilIdProfil(
                                                adherent.getProfil().getIdProfil()
                                            );
        return dateReservation.plusDays(dureeReservation.getDuree());
    }

    public long compteReservationsEnCours(Adherent adherent) {
        List<Reservation> allReservations = findByAdherentMatricule(adherent.getMatricule());
        return allReservations.stream()
                .filter(reservation -> {
                    LocalDateTime dateFin = reservation.getDateExpiration();
                    LocalDateTime now = LocalDateTime.now();
                    return now.isBefore(dateFin);
                })
                .count();
    }

    public boolean deppassementQuotaReservation(Adherent adherent) {
        Profil profil = adherent.getProfil();
        Integer quotaReservation = profil.getQuotaReservation();
        long nbReservationEnCours = compteReservationsEnCours(adherent);
        return nbReservationEnCours > quotaReservation;
    }
}
