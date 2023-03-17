package com.crownhint.simplecustomer.billing.repository;

import com.crownhint.simplecustomer.billing.models.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, String> {

    Optional<BillingDetails> findByAccountNumber(String s);
}
