package com.springjpa.bibliotheque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Penalite;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    public List<Penalite> findByAdherentIdAdherent(Integer idAdherent);
    public List<Penalite> findByAdherent(Adherent adherent);
}
