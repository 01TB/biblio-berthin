package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.entity.Adherent;
import com.springjpa.entity.DureePret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Profil;
import com.springjpa.repository.PretRepository;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private RetourPretService retourPretService;

    public Pret findById(Integer id){
        return pretRepository.findById(id).get();
    }

    public List<Pret> findAll(){
        return pretRepository.findAll();
    }

    public void save(Pret pret){
        pretRepository.save(pret);
    }

    public List<Pret> findByExemplaireIdExemplaire(Integer idExemplaire){
        return pretRepository.findByExemplaireIdExemplaire(idExemplaire);
    };

    public static boolean datesSeChevauchent(LocalDateTime debut1, LocalDateTime fin1,
                                             LocalDateTime debut2, LocalDateTime fin2) {
        return fin1.isAfter(debut2) && fin2.isAfter(debut1);
    }
}
