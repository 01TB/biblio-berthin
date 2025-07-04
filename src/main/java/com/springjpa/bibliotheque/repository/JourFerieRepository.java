package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.JourFerie;

@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, Integer> {
}
