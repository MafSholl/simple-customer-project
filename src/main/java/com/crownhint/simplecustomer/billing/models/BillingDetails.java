package com.crownhint.simplecustomer.billing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
public class BillingDetails {

    @Id
    private Long id;
    private Long accountNumber;
    private Long tarriff;
}
