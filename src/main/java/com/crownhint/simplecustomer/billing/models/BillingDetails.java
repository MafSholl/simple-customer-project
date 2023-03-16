package com.crownhint.simplecustomer.billing.models;

import com.crownhint.simplecustomer.user.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BillingDetails {
    @Id
    @NonNull
    @Column(unique = true)
    private String accountNumber;
    private Long tarriff;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private User userId;
}
