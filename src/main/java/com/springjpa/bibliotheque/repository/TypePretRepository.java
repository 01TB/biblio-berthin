package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.TypePret;

@Repository
public interface TypePretRepository extends JpaRepository<TypePret, Integer> {
}
