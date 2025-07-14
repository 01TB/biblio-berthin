package com.springjpa.bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.repository.ProfilRepository;

@Service
public class ProfilService {
    @Autowired
    private ProfilRepository profilRepository;

    public Profil findById(Integer id){
        return profilRepository.findById(id).get();
    }

    public List<Profil> findAll(){
        return profilRepository.findAll();
    }

    public void save(Profil profil){
        profilRepository.save(profil);
    }
}
