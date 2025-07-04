package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.TypePret;
import com.springjpa.bibliotheque.repository.TypePretRepository;

@Service
public class TypePretService {
    @Autowired
    private TypePretRepository typePretRepository;

    public TypePret findById(Integer id){
        return typePretRepository.findById(id).get();
    }

    public List<TypePret> findAll(){
        return typePretRepository.findAll();
    }

    public void save(TypePret typePret){
        typePretRepository.save(typePret);
    }
}
