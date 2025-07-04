package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Auteur;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
}
