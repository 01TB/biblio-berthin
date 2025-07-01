package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.DureeReservation;
import com.springjpa.repository.DureeReservationRepository;

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
}
