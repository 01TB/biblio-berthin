package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Adherent;
import com.springjpa.repository.AdherentRepository;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    public Adherent findById(Integer id){
        return adherentRepository.findById(id).get();
    }

    public List<Adherent> findAll(){
        return adherentRepository.findAll();
    }

    public void save(Adherent adherent){
        adherentRepository.save(adherent);
    }
}
