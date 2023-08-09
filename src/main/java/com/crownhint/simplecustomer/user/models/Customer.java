package com.crownhint.simplecustomer.user.models;

import com.crownhint.simplecustomer.billing.models.BillingDetails;
import com.crownhint.simplecustomer.user.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Customer")
public class Customer {

    @Id
    @SequenceGenerator(
            name = "generator",
            sequenceName = "CustomerID_Seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "generator",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NonNull
    @Column(name = "firstName")
    private String firstName;
    @NonNull
    @Column(name = "lastName")
    private String lastName;
    @NonNull
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "billing_id")
    private BillingDetails billingId;
}
