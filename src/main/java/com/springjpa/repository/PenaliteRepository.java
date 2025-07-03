package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Adherent;
import com.springjpa.entity.Penalite;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    public List<Penalite> findByAdherentidAdherent(Integer idAdherent);
    public List<Penalite> findByAdherent(Adherent adherent);
}
