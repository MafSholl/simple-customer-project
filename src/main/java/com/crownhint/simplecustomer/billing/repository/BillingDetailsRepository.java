package com.crownhint.simplecustomer.billing.repository;

import com.crownhint.simplecustomer.billing.models.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, String> {

}
