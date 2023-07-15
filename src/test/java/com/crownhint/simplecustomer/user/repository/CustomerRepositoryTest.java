package com.crownhint.simplecustomer.user.repository;

import com.crownhint.simplecustomer.billing.models.BillingDetails;
import com.crownhint.simplecustomer.user.models.Customer;
import com.crownhint.simplecustomer.user.models.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class CustomerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void repositoryExistTest() {
        assertNotNull(userRepository);
    }

    @Test
    public void whenRepositoryIsEmpty_ShouldFindNoUser() {
        Iterable<Customer> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    public void repository_canPersist() {
        BillingDetails billingDetails = new BillingDetails((1_000_000_000L + "-01"));
        this.entityManager.persist(
                new Customer("Adigun", "Lyon", "adigun@example.com", Role.CUSTOMER)
        );

        Iterable users = userRepository.findAll();
        assertThat(users).hasSize(1);

    }

    @Test
    public void usersSaved_CanBeRetrieved() {
        BillingDetails billingDetails1 = new BillingDetails((1_021_000_300L + "-01"));
        Customer customer1 = new Customer("Adigun", "Lyon", "adigun@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer1);
        BillingDetails billingDetails2 = new BillingDetails((1_021_000_001L + "-01"));
        Customer customer2 = new Customer("Hushmanni", "Balboa", "hushh@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer2);

        Iterable users = userRepository.findAll();
        assertThat(users).hasSize(2).contains(customer1, customer2);
    }

    @Test
    public void usersSaved_CanBeRetrieved_byFirstname() {
        BillingDetails billingDetails1 = new BillingDetails((1_001_000_721L + "-01"));
        Customer customer1 = new Customer("kpe", "paso", "paso@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer1);
        BillingDetails billingDetails2 = new BillingDetails((1_021_020_001L + "-01"));
        Customer customer2 = new Customer("Lekwa", "Idi", "idi@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer2);

        Customer foundCustomer = userRepository.findByFirstName(customer2.getFirstName());
        assertThat(foundCustomer).isEqualTo(customer2);
    }
    @Test
    public void usersSaved_CanbeRetrieved_byLastname() {
        BillingDetails billingDetails1 = new BillingDetails((1_921_000_001L + "-01"));
        Customer customer1 = new Customer("wonbe", "waso", "wonbe@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer1);

        BillingDetails billingDetails2 = new BillingDetails((1_021_099_001L + "-01"));
        Customer customer2 = new Customer("olamide", "wande", "kpepaso@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer2);

        Customer foundCustomer = userRepository.findByFirstName(customer2.getFirstName());
        assertThat(foundCustomer).isEqualTo(customer2);
    }
    @Test
    public void usersSaved_CanbeRetrieved_byRole() {
        Customer customer1 = new Customer("wonbe", "waso", "wonbe@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer1);

        Customer customer2 = new Customer("olamide", "wande", "kpepaso@example.com", Role.CUSTOMER);
        this.entityManager.persist(customer2);

        Customer customer3 = new Customer("olamide", "wande", "yannibo@example.com", Role.ADMIN);
        this.entityManager.persist(customer3);

        Iterable<Customer> foundUsers = userRepository.findAllByRole(Role.ADMIN);
        assertThat(foundUsers).hasSize(1).contains(customer3);
    }

}