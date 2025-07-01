package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.Abonnement;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
}
