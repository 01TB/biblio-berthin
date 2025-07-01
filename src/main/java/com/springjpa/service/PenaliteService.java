package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Penalite;
import com.springjpa.repository.PenaliteRepository;

@Service
public class PenaliteService {
    @Autowired
    private PenaliteRepository penaliteRepository;

    public Penalite findById(Integer id){
        return penaliteRepository.findById(id).get();
    }

    public List<Penalite> findAll(){
        return penaliteRepository.findAll();
    }

    public void save(Penalite penalite){
        penaliteRepository.save(penalite);
    }
}
