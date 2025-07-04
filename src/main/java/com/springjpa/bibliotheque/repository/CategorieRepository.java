package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
