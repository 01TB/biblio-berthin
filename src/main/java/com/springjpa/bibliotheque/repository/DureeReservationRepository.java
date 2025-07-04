package com.springjpa.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.bibliotheque.entity.DureeReservation;

@Repository
public interface DureeReservationRepository extends JpaRepository<DureeReservation, Integer> {
}
