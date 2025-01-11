package com.personal.finance.domain.entities.budget;

import jakarta.persistence.*;

import java.util.UUID;

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
}
