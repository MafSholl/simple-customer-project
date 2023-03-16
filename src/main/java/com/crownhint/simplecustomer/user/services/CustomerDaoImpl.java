package com.crownhint.simplecustomer.user.services;

import com.crownhint.simplecustomer.billing.dtos.BillingDetailsDto;
import com.crownhint.simplecustomer.billing.services.BillingService;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.models.enums.Role;
import com.crownhint.simplecustomer.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CustomerDaoImpl implements UserDao{

    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BillingService billingService;

    public CustomerDaoImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public UserDto createUser(CreateUserDto createUserRequest) {
        validateInputFields(createUserRequest);
        validateDuplicateEmailIfExistingInRepository(createUserRequest);
        validateEmail(createUserRequest.getEmail());
        User user = modelMapper.map(createUserRequest, User.class);
        user.setRole(Role.CUSTOMER);
        user.setBillingDetails(billingService.createBillingDetails(new BillingDetailsDto(
                createUserRequest.getFirstName(), createUserRequest.getLastName()
        )));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    private void validateDuplicateEmailIfExistingInRepository(CreateUserDto createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) throw new SimpleCustomerException("Email already exist!");
    }

    private static void validateInputFields(CreateUserDto createUserRequest) {
        if (createUserRequest.getFirstName() == null || createUserRequest.getLastName() == null ||
                createUserRequest.getEmail() == null || createUserRequest.getRole() == null
        ) throw new SimpleCustomerException("Request field cannot be null");
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
        User optionalUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new SimpleCustomerException("User does not exist!"));
        return modelMapper.map(optionalUser, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        if (userList.size() == userRepository.count()) {
            for (User user : userList) {
                userDtoList.add(modelMapper.map(user, UserDto.class));
            }
        }
        return userDtoList;
    }

}
