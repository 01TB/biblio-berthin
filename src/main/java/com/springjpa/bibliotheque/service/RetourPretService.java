package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.RetourPret;
import com.springjpa.bibliotheque.repository.RetourPretRepository;

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

    public RetourPret findByPretIdPret(Integer idPret){
        return retourPretRepository.findByPretIdPret(idPret);
    };

    public boolean existsByPretIdPret(Integer idPret){
        return retourPretRepository.existsByPretIdPret(idPret);
    };
}
