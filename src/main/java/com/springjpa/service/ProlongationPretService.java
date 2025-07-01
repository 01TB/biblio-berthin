package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.ProlongationPret;
import com.springjpa.repository.ProlongationPretRepository;

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

    public void save(ProlongationPret prolongationPret){
        prolongationPretRepository.save(prolongationPret);
    }
}
