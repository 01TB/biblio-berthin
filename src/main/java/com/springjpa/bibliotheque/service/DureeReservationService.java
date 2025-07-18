package com.springjpa.bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.DureeReservation;
import com.springjpa.bibliotheque.repository.DureeReservationRepository;

@Service
public class DureeReservationService {
    @Autowired
    private DureeReservationRepository dureeReservationRepository;

    public DureeReservation findById(Integer id){
        return dureeReservationRepository.findById(id).get();
    }

    public List<DureeReservation> findAll(){
        return dureeReservationRepository.findAll();
    }

    public void save(DureeReservation dureeReservation){
        dureeReservationRepository.save(dureeReservation);
    }

    public DureeReservation findDureeReservationByProfilIdProfil(Integer idProfil) {
        return dureeReservationRepository.findDureeReservationByProfilIdProfil(idProfil);
    };
}
