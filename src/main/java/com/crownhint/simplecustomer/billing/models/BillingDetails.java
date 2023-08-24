package com.crownhint.simplecustomer.billing.models;

import com.crownhint.simplecustomer.user.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "billing_details")
public class BillingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NonNull
    @Column(unique = true)
    @Pattern(regexp = "^[\\d]{10}(\\-[\\d]{2})$")
    private String accountNumber;
    @Column(nullable = false)
    private Double tariff;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private User userId;
}
