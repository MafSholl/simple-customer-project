package com.crownhint.simplecustomer.user.repository;

import com.crownhint.simplecustomer.user.models.Customer;
import com.crownhint.simplecustomer.user.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {
    Customer findByFirstName(String firstName);
    Iterable<Customer> findAllByRole(Role admin);
    Optional<Customer> findByEmail(String email);

}
