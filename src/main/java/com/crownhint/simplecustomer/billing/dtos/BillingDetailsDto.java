package com.crownhint.simplecustomer.billing.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class BillingDetailsDto {

    @NonNull
    private String customerFirstName;
    @NonNull
    private String customerLastName;
    private String accountNumber;
    private Long tarriff;
}
