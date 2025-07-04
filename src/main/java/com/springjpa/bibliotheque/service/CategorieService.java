package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.Categorie;
import com.springjpa.bibliotheque.repository.CategorieRepository;

@Service
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie findById(Integer id){
        return categorieRepository.findById(id).get();
    }

    public List<Categorie> findAll(){
        return categorieRepository.findAll();
    }

    public void save(Categorie categorie){
        categorieRepository.save(categorie);
    }
}
