package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.QuotaTypePret;

@Repository
public interface QuotaTypePretRepository extends JpaRepository<QuotaTypePret, Integer> {
    public QuotaTypePret findByProfilIdProfilAndTypePretIdTypePret(Integer idProfil, Integer idTypePret);
}
