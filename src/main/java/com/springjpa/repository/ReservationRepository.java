package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    public List<Reservation> findByExemplaireIdExemplaire(Integer idExemplaire);
    public List<Reservation> findByAdherentMatricule(Integer matriculeAdherent);
}
