package com.crownhint.simplecustomer.user.models;

import com.crownhint.simplecustomer.billing.models.BillingDetails;
import com.crownhint.simplecustomer.user.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`User`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(name = "firstname")
    private String firstName;
    @NonNull
    @Column(name = "lastName")
    private String lastName;
    @NonNull
    @Column(name = "email", unique = true)
    private String email;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_accountNumber")
    private BillingDetails billingDetails;

}
