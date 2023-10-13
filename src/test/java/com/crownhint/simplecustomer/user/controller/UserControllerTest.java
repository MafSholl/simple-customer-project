package com.crownhint.simplecustomer.user.controller;

import com.crownhint.simplecustomer.auth.jwt.JwtAuthenticationFilter;
import com.crownhint.simplecustomer.auth.jwt.JwtService;
import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.LoginDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("dev")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private CreateUserDto request;
    private UserDto responseBody;
//    @InjectMocks private JwtAuthenticationFilter jwtAuthenticationFilter;
//    @Autowired JwtService jwtService;
//    @Autowired UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        CreateUserDto request = CreateUserDto.builder()
                .firstName("Ijaduola")
                .lastName("Kashimawo")
                .email("kaja@example.com")
                .role("user")
                .build();
        this.request = request;

        UserDto responseBody  = UserDto.builder()
                .firstName("Ijaduola")
                .lastName("Kashimawo")
                .email("kaja@example.com")
                .build();
        this.responseBody = responseBody;
    }
    @Test
    public void userControllerExistTest() {
        UserController userController = new UserController();
        assertThat(userController).isNotNull();
    }

    @Test
    public void getWhenSaveEndpointCalled_ThenEmptyResponseTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/customers/save-customer"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveEndpointCalled_ParamsExpectedTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/customers/save-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveEndpointCalled_AndIncorrectRequestBody_ThenReturns400Test() throws Exception {
        CreateUserDto request = new CreateUserDto();
        this.mockMvc.perform(post("/api/v1/customers/save-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveEndpointCalled_BusinessLogicIsCalledTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/customers/save-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(userService, times(1)).createUser(any(CreateUserDto.class));
    }

    @Test
    public void whenSaveEndpointCalled_BusinessLogicReturns_BodyAnd200Test() throws Exception {
        ApiResponse expectedResponse = ApiResponse.builder()
                .status("Success")
                .message("User created successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        when(userService.createUser(any(CreateUserDto.class))).thenReturn(responseBody);

        MvcResult result =this.mockMvc.perform(post("/api/v1/customers/save-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    public void whenGetOneCustomerEndpointCalled_ThenReturnsCustomerTest() throws Exception {
        UserDto responseBody = UserDto.builder()
                .firstName(this.request.getFirstName())
                .lastName(this.request.getLastName())
                .email(this.request.getEmail())
                .build();
        ApiResponse expectedResponse = ApiResponse.builder()
                .status("Success")
                .message("User found successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        when(userService.findUser(any(String.class))).thenReturn(responseBody);

        MvcResult result = this.mockMvc.perform(get("/api/v1/customers/find-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", request.getEmail()))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
    }
    @Test
    public void whenFindAllCustomerEndpointCalled_ThenReturnsCustomerListTest() throws Exception {
        UserDto responseBody = UserDto.builder()
                .firstName(this.request.getFirstName())
                .lastName(this.request.getLastName())
                .email(this.request.getEmail())
                .build();
        List<UserDto> usersList = Arrays.asList(
                responseBody
        );
        ApiResponse expectedResponse = ApiResponse.builder()
                .status("Success")
                .message("All customers found successfully")
                .data(usersList)
                .statusCode(HttpStatus.OK.value())
                .build();
        when(userService.findAllUsers()).thenReturn(usersList);

        MvcResult result = this.mockMvc.perform(get("/api/v1/customers/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    void testThat_UserCanLogin() throws Exception {
        UserDto responseBody = UserDto.builder()
                .firstName(this.request.getFirstName())
                .lastName(this.request.getLastName())
                .email(this.request.getEmail())
                .build();
        ApiResponse expectedResponse = ApiResponse.builder()
                .status("Success")
                .message("Login successful")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
//        when(userService.findUser(any(String.class))).thenReturn(responseBody);
        when(userService.login(any())).thenReturn(responseBody);

        MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LoginDto.builder()
                        .username(this.request.getEmail())
                        .password("123456")
                        .build()))
                )
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));

    }


}