package com.springjpa.bibliotheque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.bibliotheque.entity.ProlongationPret;

@Repository
public interface ProlongationPretRepository extends JpaRepository<ProlongationPret, Integer> {
    public List<ProlongationPret> findByPretIdPret(Integer idPret);
    public List<ProlongationPret> findByPretAdherentIdAdherent(Integer idAdherent);
}