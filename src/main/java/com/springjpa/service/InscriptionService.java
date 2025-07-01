package com.springjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.Abonnement;
import com.springjpa.repository.InscriptionRepository;

@Service
public class InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    public Abonnement findById(Integer id){
        return inscriptionRepository.findById(id).get();
    }

    public List<Abonnement> findAll(){
        return inscriptionRepository.findAll();
    }

    public void save(Abonnement inscription){
        inscriptionRepository.save(inscription);
    }
}
