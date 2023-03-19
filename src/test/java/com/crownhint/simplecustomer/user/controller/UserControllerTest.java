package com.crownhint.simplecustomer.user.controller;

import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private CreateUserDto request;
    private UserDto responseBody;

    @BeforeEach
    void setUp() {
        CreateUserDto request = CreateUserDto.builder()
                .firstName("Ijaduola")
                .lastName("Kashimawo")
                .email("kaja@example.com")
                .role("customer")
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
        this.mockMvc.perform(post("/api/v1/customer/save-customer"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveEndpointCalled_ParamsExpectedTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/customer/save-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveEndpointCalled_AndIncorrectRequestBody_ThenReturns400Test() throws Exception {
        CreateUserDto request = new CreateUserDto();
        this.mockMvc.perform(post("/api/v1/customer/save-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSaveEndpointCalled_BusinessLogicIsCalledTest() throws Exception {
        this.mockMvc.perform(post("/api/v1/customer/save-customer")
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
                .message("Customer created successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        when(userService.createUser(any(CreateUserDto.class))).thenReturn(responseBody);

        MvcResult result =this.mockMvc.perform(post("/api/v1/customer/save-customer")
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
                .message("Customer found successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        when(userService.findUser(any(String.class))).thenReturn(responseBody);

        MvcResult result = this.mockMvc.perform(get("/api/v1/customer/find-customer")
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

        MvcResult result = this.mockMvc.perform(get("/api/v1/customer/all-customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
    }


}