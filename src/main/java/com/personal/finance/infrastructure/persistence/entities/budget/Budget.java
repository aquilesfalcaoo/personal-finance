package com.personal.finance.infrastructure.persistence.entities.budget;

import com.personal.finance.application.dto.budget.BudgetRequestPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String category;

    @Column(name = "maximum_spend", nullable = false)
    private double maximumSpend;

    @Column(nullable = false)
    private String theme;

    public Budget(BudgetRequestPayload data) {
        this.category = data.category();
        this.maximumSpend = data.maximumSpend();
        this.theme = data.theme();
    }
}
