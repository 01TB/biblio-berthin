package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.RetourPret;

@Repository
public interface RetourPretRepository extends JpaRepository<RetourPret, Integer> {
}
