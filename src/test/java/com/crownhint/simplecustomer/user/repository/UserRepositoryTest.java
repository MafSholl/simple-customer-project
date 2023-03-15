package com.crownhint.simplecustomer.user.repository;

import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.models.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

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
        Iterable<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    public void repository_canPersist() {
        this.entityManager.persist(
                new User("Adigun", "Lyon", "adigun@example.com", Role.CUSTOMER)
        );

        Iterable users = userRepository.findAll();
        assertThat(users).hasSize(1);

    }

    @Test
    public void usersSaved_CanBeRetrieved() {
        User user1 = new User("Adigun", "Lyon", "adigun@example.com", Role.CUSTOMER);
        this.entityManager.persist(user1);
        User user2 = new User("Hushmanni", "Balboa", "hushh@example.com", Role.CUSTOMER);
        this.entityManager.persist(user2);

        Iterable users = userRepository.findAll();
        assertThat(users).hasSize(2).contains(user1, user2);
    }

    @Test
    public void usersSaved_CanBeRetrieved_byFirstname() {
        User user1 = new User("kpe", "paso", "paso@example.com", Role.CUSTOMER);
        this.entityManager.persist(user1);
        User user2 = new User("Lekwa", "Idi", "idi@example.com", Role.CUSTOMER);
        this.entityManager.persist(user2);

        User foundUser = userRepository.findByFirstName(user2.getFirstName());
        assertThat(foundUser).isEqualTo(user2);
    }
    @Test
    public void usersSaved_CanbeRetrieved_byLastname() {
        User user1 = new User("wonbe", "waso", "wonbe@example.com", Role.CUSTOMER);
        this.entityManager.persist(user1);
        User user2 = new User("olamide", "wande", "kpepaso@example.com", Role.CUSTOMER);
        this.entityManager.persist(user2);

        User foundUser = userRepository.findByFirstName(user2.getFirstName());
        assertThat(foundUser).isEqualTo(user2);
    }
    @Test
    public void usersSaved_CanbeRetrieved_byRole() {
        User user1 = new User("wonbe", "waso", "wonbe@example.com", Role.CUSTOMER);
        this.entityManager.persist(user1);
        User user2 = new User("olamide", "wande", "kpepaso@example.com", Role.CUSTOMER);
        this.entityManager.persist(user2);
        User user3 = new User("olamide", "wande", "yannibo@example.com", Role.ADMIN);
        this.entityManager.persist(user3);

        Iterable<User> foundUsers = userRepository.findAllByRole(Role.ADMIN);
        assertThat(foundUsers).hasSize(1).contains(user3);
    }

}