package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Langue;

@Repository
public interface LangueRepository extends JpaRepository<Langue, Integer> {
}
