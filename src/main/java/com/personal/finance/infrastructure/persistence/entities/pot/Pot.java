package com.personal.finance.domain.entities.pot;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Pot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double target;

    @Column(nullable = false)
    private String theme;
}
