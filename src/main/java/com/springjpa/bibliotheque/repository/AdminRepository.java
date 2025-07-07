package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public Admin findByMatriculeAndPassword(Integer matriculeAdmin, String password);
}
