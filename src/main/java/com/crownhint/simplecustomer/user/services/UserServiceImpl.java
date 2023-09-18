package com.crownhint.simplecustomer.user.services;

import com.crownhint.simplecustomer.auth.jwt.JwtService;
import com.crownhint.simplecustomer.billing.dtos.BillingDetailsDto;
import com.crownhint.simplecustomer.billing.services.BillingService;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.LoginDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.models.enums.Role;
import com.crownhint.simplecustomer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BillingService billingService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BillingService billingService,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.billingService = billingService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Override
    public UserDto createUser(CreateUserDto createUserRequest) {
        validateInputFields(createUserRequest);
        validateDuplicateEmailIfExistingInRepository(createUserRequest);
        validateEmail(createUserRequest.getEmail());
        User user = modelMapper.map(createUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(
                createUserRequest.getPassword() != null ? createUserRequest.getPassword() : "00000"
        ));
        user.setRole(
                (createUserRequest.getRole().equalsIgnoreCase("user")) ? Role.CUSTOMER : Role.STAFF
        );
        user.setBillingId(billingService.createBillingDetails(new BillingDetailsDto(
                createUserRequest.getFirstName(), createUserRequest.getLastName()
        )));
        User savedUser = userRepository.save(user);
        var token = jwtService.generateToken(savedUser);
        log.info("User -> {} created successfully", savedUser);
        UserDto returnedUser = modelMapper.map(savedUser, UserDto.class);
        returnedUser.setToken(token);
        return returnedUser;
    }

    private void validateDuplicateEmailIfExistingInRepository(CreateUserDto createUserRequest) {

        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            log.info("Email already exist: -> {}", createUserRequest.getEmail());
            throw new SimpleCustomerException("Email already exist!");
        }
    }

    private static void validateInputFields(CreateUserDto createUserRequest) {
        if (createUserRequest == null) throw new SimpleCustomerException("Request cannot be null");
        if (createUserRequest.getEmail() == null || createUserRequest.getFirstName() == null ||
                createUserRequest.getLastName() == null ) throw new SimpleCustomerException("Request cannot be null");
    }

    private static void validateEmail(String email) {
        String emailInputRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        boolean result = Pattern.matches(emailInputRegex, email);
        if (!result) throw new SimpleCustomerException("Wrong email input format. Check ");
    }


    @Override
    public UserDto findUser(String email) {
        if(email == null) throw new SimpleCustomerException("Email cannot be null");
        validateEmail(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new SimpleCustomerException("User does not exist!"));
        log.info("User found id:{}", user.getId());
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        log.info("User list count: -> {}", userList.size());
        List<UserDto> userDtoList;
        userDtoList = userList.stream()
                    .map(customer -> modelMapper.map(customer, UserDto.class))
                    .collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto login(LoginDto loginRequest) {
        if (loginRequest == null) throw new SimpleCustomerException("Request cannot be empty");
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null)
            throw new SimpleCustomerException("Username or password cannot be null");
        User user = userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow(()-> new SimpleCustomerException("User does not exist"));
        user.setLogin(true);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new SimpleCustomerException("User does not exist"));
    }
}
