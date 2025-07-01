package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.RetourPret;
import com.springjpa.repository.RetourPretRepository;

@Service
public class RetourPretService {
    @Autowired
    private RetourPretRepository retourPretRepository;

    public RetourPret findById(Integer id){
        return retourPretRepository.findById(id).get();
    }

    public List<RetourPret> findAll(){
        return retourPretRepository.findAll();
    }

    public void save(RetourPret retourPret){
        retourPretRepository.save(retourPret);
    }
}
