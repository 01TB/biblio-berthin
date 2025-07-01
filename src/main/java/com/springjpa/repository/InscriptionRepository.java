package com.springjpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Abonnement;

@Repository
public interface InscriptionRepository extends JpaRepository<Abonnement, Integer> {
    Optional<Abonnement> findTopByAdherantIdAdherantAndEtatOrderByDateInscriptionDesc(Integer adherantId, boolean etat);
}
