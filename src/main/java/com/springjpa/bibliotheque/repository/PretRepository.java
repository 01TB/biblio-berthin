package com.springjpa.bibliotheque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    public List<Pret> findByExemplaireIdExemplaire(Integer idExemplaire);
    public List<Pret> findByAdherentIdAdherent(Integer idAdherernt);
}
