package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.TypePenaliteProfil;
import com.springjpa.bibliotheque.repository.TypePenaliteProfilRepository;

@Service
public class TypePenaliteProfilService {
    @Autowired
    private TypePenaliteProfilRepository typePenaliteProfilRepository;

    public TypePenaliteProfil findById(Integer id){
        return typePenaliteProfilRepository.findById(id).get();
    }

    public List<TypePenaliteProfil> findAll(){
        return typePenaliteProfilRepository.findAll();
    }

    public void save(TypePenaliteProfil typePenaliteProfil){
        typePenaliteProfilRepository.save(typePenaliteProfil);
    }

    public TypePenaliteProfil findByProfilIdProfil(Integer idProfil){
        return typePenaliteProfilRepository.findByProfilIdProfil(idProfil);
    };
}
