package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Abonnement;
import java.util.List;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    List<Abonnement> findByIdAbonnement(Integer idAdherent);
    List<Abonnement> findByAdherentMatricule(Integer adherentMatricule);
}