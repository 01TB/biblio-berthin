package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.Adherent;

@Repository
public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
    public Adherent findByMatricule(Integer matriculeAdherent);
    public Adherent findByMatriculeAndPassword(Integer matriculeAdherent, String password);
}
