package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Livre;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
}
