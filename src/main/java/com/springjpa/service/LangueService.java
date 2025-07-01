package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Langue;
import com.springjpa.repository.LangueRepository;

@Service
public class LangueService {
    @Autowired
    private LangueRepository langueRepository;

    public Langue findById(Integer id){
        return langueRepository.findById(id).get();
    }

    public List<Langue> findAll(){
        return langueRepository.findAll();
    }

    public void save(Langue langue){
        langueRepository.save(langue);
    }
}
