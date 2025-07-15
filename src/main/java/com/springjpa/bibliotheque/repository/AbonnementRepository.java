package com.springjpa.bibliotheque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.bibliotheque.entity.Abonnement;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    public List<Abonnement> findByIdAbonnement(Integer idAdherent);
    public List<Abonnement> findByAdherentMatricule(Integer adherentMatricule);
}