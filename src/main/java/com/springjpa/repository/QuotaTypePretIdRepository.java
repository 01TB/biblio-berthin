package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.QuotaTypePretId;

@Repository
public interface QuotaTypePretIdRepository extends JpaRepository<QuotaTypePretId, Integer> {
}
