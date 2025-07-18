package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.bibliotheque.entity.StatutReservation;

@Repository
public interface StatutReservationRepository extends JpaRepository<StatutReservation, Integer> {
}
