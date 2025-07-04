package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.Editeur;
import com.springjpa.bibliotheque.repository.EditeurRepository;

@Service
public class EditeurService {
    @Autowired
    private EditeurRepository editeurRepository;

    public Editeur findById(Integer id){
        return editeurRepository.findById(id).get();
    }

    public List<Editeur> findAll(){
        return editeurRepository.findAll();
    }

    public void save(Editeur editeur){
        editeurRepository.save(editeur);
    }
}
