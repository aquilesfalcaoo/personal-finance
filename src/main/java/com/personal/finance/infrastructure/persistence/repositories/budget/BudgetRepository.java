package com.personal.finance.infrastructure.persistence.repositories.budget;

import com.personal.finance.infrastructure.persistence.entities.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> { }
