package com.crownhint.simplecustomer.user.services;

import com.crownhint.simplecustomer.billing.dtos.BillingDetailsDto;
import com.crownhint.simplecustomer.billing.services.BillingService;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.user.models.Customer;
import com.crownhint.simplecustomer.user.models.enums.Role;
import com.crownhint.simplecustomer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BillingService billingService;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BillingService billingService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.billingService = billingService;
    }
    @Override
    public UserDto createUser(CreateUserDto createUserRequest) {
        validateInputFields(createUserRequest);
        validateDuplicateEmailIfExistingInRepository(createUserRequest);
        validateEmail(createUserRequest.getEmail());
        Customer customer = modelMapper.map(createUserRequest, Customer.class);
        customer.setRole(
                (createUserRequest.getRole().equalsIgnoreCase("customer")) ? Role.CUSTOMER : Role.STAFF
        );
        customer.setBillingId(billingService.createBillingDetails(new BillingDetailsDto(
                createUserRequest.getFirstName(), createUserRequest.getLastName()
        )));
        Customer savedCustomer = userRepository.save(customer);
        return modelMapper.map(savedCustomer, UserDto.class);
    }

    private void validateDuplicateEmailIfExistingInRepository(CreateUserDto createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) throw new SimpleCustomerException("Email already exist!");
    }

    private static void validateInputFields(CreateUserDto createUserRequest) {
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
        Customer optionalCustomer = userRepository.findByEmail(email)
                .orElseThrow(()-> new SimpleCustomerException("User does not exist!"));
        return modelMapper.map(optionalCustomer, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<Customer> customerList = userRepository.findAll();
        log.info("Customer list count: -> {}", customerList.size());
        List<UserDto> userDtoList = new ArrayList<>();
        System.out.println(Arrays.deepToString(customerList.toArray()));
        userDtoList = customerList.stream()
                    .map(customer -> modelMapper.map(customer, UserDto.class))
                    .collect(Collectors.toList());
        return userDtoList;
    }

}
