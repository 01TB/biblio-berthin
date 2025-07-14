package com.springjpa.bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.ProlongationPret;
import com.springjpa.bibliotheque.repository.ProlongationPretRepository;

@Service
public class ProlongationPretService {
    @Autowired
    private ProlongationPretRepository prolongationPretRepository;

    public ProlongationPret findById(Integer id){
        return prolongationPretRepository.findById(id).get();
    }

    public List<ProlongationPret> findAll(){
        return prolongationPretRepository.findAll();
    }

    public List<ProlongationPret> findByPretIdPret(Integer idPret) {
        return prolongationPretRepository.findByPretIdPret(idPret);
    }

    public void save(ProlongationPret prolongationPret){
        prolongationPretRepository.save(prolongationPret);
    }
}