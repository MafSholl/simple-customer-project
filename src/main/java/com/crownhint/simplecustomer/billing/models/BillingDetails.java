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
@Entity
public class BillingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(unique = true)
    @Pattern(regexp = "^[\\d]{10}(\\-[\\d]{2})$")
    private String accountNumber;
    private Long tarriff;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customerId")
    private User userId;
}
