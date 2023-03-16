package com.crownhint.simplecustomer.billing.services;

import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.billing.dtos.BillingDetailsDto;
import com.crownhint.simplecustomer.billing.models.BillingDetails;
import com.crownhint.simplecustomer.billing.repository.BillingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class BillingServiceImpl implements BillingService{

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

    private Long baseAccountNumber = 1_000_000_000L;
    @Override
    public BillingDetails createBillingDetails(BillingDetailsDto createBillingDetailsRequest) {
        if (createBillingDetailsRequest == null) throw new SimpleCustomerException("Billing request cannot be null");
        BillingDetails billingDetails = BillingDetails.builder()
                .accountNumber(accountNumberGenerator())
                .tarriff(createBillingDetailsRequest.getTarriff())
                .build();
        BillingDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);
        return savedBillingDetails;
    }

    private String accountNumberGenerator() {
        String generatedAccountNumber = "" + this.baseAccountNumber++ +"-01";
        return generatedAccountNumber;
    }
}
