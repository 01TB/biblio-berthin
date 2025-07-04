package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.DureePret;

@Repository
public interface DureePretRepository extends JpaRepository<DureePret, Integer> {
    public DureePret findByProfilIdProfil(Integer idProfil);
}
