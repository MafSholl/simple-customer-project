package com.crownhint.simplecustomer.billing.services;

import com.crownhint.simplecustomer.billing.dtos.BillingDetailsDto;
import com.crownhint.simplecustomer.billing.models.BillingDetails;

public interface BillingService {
    BillingDetails createBillingDetails(BillingDetailsDto createBillingDetailsRequest);
}
