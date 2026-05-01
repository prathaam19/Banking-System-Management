package com.bank.BankingApplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
    public class Branch {
        @Id
        private Long id;
        private String name;
        private double latitude;
        private double longitude;
    }

