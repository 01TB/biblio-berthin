package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.RetourPret;

@Repository
public interface RetourPretRepository extends JpaRepository<RetourPret, Integer> {
    public RetourPret findByPretIdPret(Integer idPret);
    public boolean existsByPretIdPret(Integer idPret);
}
