package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.bibliotheque.entity.Admin;
import com.springjpa.bibliotheque.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin findById(Integer id){
        return adminRepository.findById(id).get();
    }

    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    public void save(Admin admin){
        adminRepository.save(admin);
    }

    public Admin findByMatriculeAndPassword(Integer matriculeAdmin, String password){
        return adminRepository.findByMatriculeAndPassword(matriculeAdmin,password);
    };

    public boolean isAdmin(Integer matriculeAdmin, String password){
        return findByMatriculeAndPassword(matriculeAdmin, password)!=null;
    }
}
