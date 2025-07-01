package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Profil;
import com.springjpa.repository.ProfilRepository;

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
