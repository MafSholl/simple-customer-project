package com.crownhint.simplecustomer.billing.services;

import com.crownhint.simplecustomer.billing.dtos.BillingDetailsDto;
import com.crownhint.simplecustomer.billing.models.BillingDetails;
import com.crownhint.simplecustomer.billing.repository.BillingDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("dev")
class BillingServiceImplTest {

    @Autowired
    private BillingService billingService;
    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

    @Test
    void testThatBillingDetailsExist() {
        BillingDetails billingDetails = new BillingDetails();
        assertThat(billingDetails).isNotNull();
    }

    @Test
    void testThatBillingDetails_IsGenerated() {
        BillingDetailsDto createBillingDetailsRequest = BillingDetailsDto.builder()
                .customerFirstName("Ajayi")
                .customerLastName("Modakeke")
                .tarriff(0.00)
                .build();
        BillingDetails billingDetails = billingService.createBillingDetails(createBillingDetailsRequest);
        assertThat(billingDetails).isNotNull();
        assertThat(billingDetails.getAccountNumber()).isEqualTo(1_000_000_001 +"-01");
    }

    @Test
    void testThatBillingDetails_isPersisted() {
        BillingDetailsDto createBillingDetailsRequest = BillingDetailsDto.builder()
                .customerFirstName("Adewale")
                .customerLastName("Olayinka")
                .tarriff(0.00)
                .build();
        BillingDetails billingDetails = billingService.createBillingDetails(createBillingDetailsRequest);
        Optional<BillingDetails> repoBillingDetails = billingDetailsRepository.findByAccountNumber(1_000_000_000 +"-01");
        assertThat(repoBillingDetails.get().getAccountNumber()).isEqualTo(billingDetails.getAccountNumber());
    }

}