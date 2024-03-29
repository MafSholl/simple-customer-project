package com.crownhint.simplecustomer.user.services;

import com.crownhint.simplecustomer.security.jwt.JwtService;
import com.crownhint.simplecustomer.billing.services.BillingService;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.models.enums.Role;
import com.crownhint.simplecustomer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@EnableMethodSecurity
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ActiveProfiles("dev")
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl customerDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BillingService billingService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        customerDao = new UserServiceImpl(
                userRepository,
                modelMapper,
                billingService,
                passwordEncoder,
                jwtService
        );
    }

    @Test
    public void userExistTest() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void userCanBeCreated() {
        CreateUserDto createUserDto = new CreateUserDto(
                "Turaya", "Abeni-Agbon", "abeniagbon@hotmail.com", "User"
        );
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, modelMapper, billingService,passwordEncoder,jwtService);
        UserDto newUser = userServiceImpl.createUser(createUserDto);
        assertEquals(createUserDto.getFirstName(), newUser.getFirstName());
    }

    @Test
    public void userCreated_IsPersisted() {
        CreateUserDto createUserDto = new CreateUserDto(
                "Sam", "Igbenidion", "sameigbinedion@example.com", "User"
        );
        UserDto newUser =  customerDao.createUser(createUserDto);
        User savedUser = userRepository.findByFirstName(createUserDto.getFirstName());
        assertEquals(newUser.getEmail(), savedUser.getEmail());
    }

    @Test
    public void userPersisted_hasAllFieldsSet() {
        CreateUserDto createUserDto = new CreateUserDto(
                "Samuel", "Olorunsola", "titobi@example.com", "User"
        );
        UserDto newUser =  customerDao.createUser(createUserDto);
        User savedUser = userRepository.findByFirstName(createUserDto.getFirstName());
        assertEquals(createUserDto.getFirstName(), savedUser.getFirstName());
        assertEquals(createUserDto.getLastName(), savedUser.getLastName());
        assertEquals(createUserDto.getEmail(), savedUser.getEmail());
        assertEquals(Role.class, savedUser.getRole().getClass() );
        assertNotNull(savedUser.getBillingId());
    }

    @Test
    public void nullRequest_ThrowsException() {
        CreateUserDto createUserRequest = new CreateUserDto();
        assertThrows(SimpleCustomerException.class, ()->customerDao.createUser(createUserRequest));
    }

    @Test
    public void userWithExistingEmail_ThrowsException() {
        CreateUserDto request1 = new CreateUserDto(
                "Samuel", "Olorunsola", "sam@example.com", "User"
        );
        customerDao.createUser(request1);

        CreateUserDto request2 = new CreateUserDto(
                "Sam", "Olorun", "sam@example.com", "User"
        );
        assertThrows(SimpleCustomerException.class, ()-> customerDao.createUser(request2));
    }

    @Test
    public void incorrectEmailFormat_ThrowsException() {
        CreateUserDto request1 = new CreateUserDto(
                "doja", "Olusola", "doja@example.com", "User"
        );
        customerDao.createUser(request1);

        CreateUserDto request2 = new CreateUserDto(
                "dupe", "bawa", "ss;g@example.com", "User"
        );
        assertThrows(SimpleCustomerException.class, ()-> customerDao.createUser(request2));
    }

    @Test
    public void oneUser_canBeQueried() {
        CreateUserDto request = new CreateUserDto(
                "sumbo", "unicorn", "unicorn@example.com", "User"
        );
        CreateUserDto request1 = new CreateUserDto(
                "cobalt", "coppa", "coppa@example.com", "User"
        );
        customerDao.createUser(request);
        customerDao.createUser(request1);
        UserDto foundUser = customerDao.findUser(request.getEmail());
        assertEquals(request.getEmail(), foundUser.getEmail());
    }

    @Test
    public void nonExistingUserEmailSearch_throwsException() {
        CreateUserDto request = new CreateUserDto(
                "crisux", "latino", "latino@example.com", "User"
        );
        CreateUserDto request1 = new CreateUserDto(
                "logan", "tripartite", "logan@example.com", "User"
        );
        customerDao.createUser(request);
        customerDao.createUser(request1);
        assertThrows(SimpleCustomerException.class, ()->customerDao.findUser("arugbo@gmail.com"));
    }

    @Test
    public void allUsers_canBeQueried() {
        CreateUserDto request = new CreateUserDto(
                "hillsong", "deeper", "hillsong@example.com", "User"
        );
        CreateUserDto request1 = new CreateUserDto(
                "singer", "girlie", "girlie@example.com", "User"
        );
        CreateUserDto request2 = new CreateUserDto(
                "hallelujah", "abidogun", "habidoun@example.com", "User"
        );
        UserDto savedUser1 = customerDao.createUser(request);
        UserDto savedUser2 = customerDao.createUser(request1);
        UserDto savedUser3 = customerDao.createUser(request2);
        List<UserDto> usersList = customerDao.findAllUsers();
        assertThat(usersList).isNotNull();
//        assertThat(usersList.size()).isEqualTo(3);
    }
}