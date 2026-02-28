package com.github.annarybina.creditapplicationservice.repository;

import com.github.annarybina.creditapplicationservice.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
}
