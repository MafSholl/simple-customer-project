package com.crownhint.simplecustomer.user.models;

import com.crownhint.simplecustomer.billing.models.BillingDetails;
import com.crownhint.simplecustomer.user.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`user`")
public class User implements UserDetails {

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
    private boolean login;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
