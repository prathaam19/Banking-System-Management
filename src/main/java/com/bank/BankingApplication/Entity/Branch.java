package com.bank.BankingApplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Branch name is required")
    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @NotBlank(message = "Branch code is required")
    @Column(name = "branch_code", unique = true, nullable = false)
    private String branchCode;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Country is required")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "PIN code is required")
    @Column(name = "pin_code", nullable = false)
    private String pinCode;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false)
    private Double longitude;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Relationships
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    // Helper methods
    public void addAccount(Account account) {
        accounts.add(account);
        account.setBranch(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setBranch(null);
    }

    public String getFullAddress() {
        return address + ", " + city + ", " + state + " - " + pinCode + ", " + country;
    }
}

