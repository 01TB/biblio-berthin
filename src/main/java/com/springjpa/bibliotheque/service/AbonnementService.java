package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.Abonnement;
import com.springjpa.bibliotheque.repository.AbonnementRepository;

@Service
public class AbonnementService {
    @Autowired
    private AbonnementRepository abonnementRepository;

    public Abonnement findById(Integer id){
        return abonnementRepository.findById(id).get();
    }

    public List<Abonnement> findAll(){
        return abonnementRepository.findAll();
    }

    public void save(Abonnement abonnement){
        abonnementRepository.save(abonnement);
    }

    public List<Abonnement> findByAdherentMatricule(Integer adherentMatricule) {
        return abonnementRepository.findByAdherentMatricule(adherentMatricule);
    }
}