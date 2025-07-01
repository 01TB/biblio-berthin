package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.JourFerie;
import com.springjpa.repository.JourFerieRepository;

@Service
public class JourFerieService {
    @Autowired
    private JourFerieRepository jourFerieRepository;

    public JourFerie findById(Integer id){
        return jourFerieRepository.findById(id).get();
    }

    public List<JourFerie> findAll(){
        return jourFerieRepository.findAll();
    }

    public void save(JourFerie jourFerie){
        jourFerieRepository.save(jourFerie);
    }
}
