package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.TypePenaliteProfil;

@Repository
public interface TypePenaliteProfilRepository extends JpaRepository<TypePenaliteProfil, Integer> {
    public TypePenaliteProfil findByProfilIdProfil(Integer idProfil);
}
