package com.bramah.customerservice.web;

import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.entities.Customer;
import com.bramah.customerservice.exceptions.CustomerNotFoundException;
import com.bramah.customerservice.security.SecurityInsecureConfig;
import com.bramah.customerservice.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@Import(SecurityInsecureConfig.class)
class CustomerControllerTest {


    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<CustomerDto> customers;

    @BeforeEach
    void setUp() {
        this.customers = List.of(
                CustomerDto.builder().id("c1").firstName("Noufala").lastName("MOUNPAIN").email("noufala@gmail.com").build(),
                CustomerDto.builder().id("c2").firstName("Ibrahima").lastName("NJUTAOMVOUI").email("bramah@gmail.com").build(),
                CustomerDto.builder().id("c3").firstName("Soumi").lastName("MANDOU").email("soumi@gmail.com").build()
        );
    }


    @Test
    void shouldGetAllCustomers() throws Exception {

        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(customers)));
    }

    @Test
    void shouldGetCustomerById() throws Exception {
        String id = "c1";
        Mockito.when(customerService.findCustomerById(id)).thenReturn(customers.get(0));
        mockMvc.perform(get("/api/v1/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customers.get(0))));
    }

    @Test
    void shouldNotGetCustomerByInvalidId() throws Exception {
        String id = "c8";
        Mockito.when(customerService.findCustomerById(id)).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(get("/api/v1/customers/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void shouldsearchCustomers() throws Exception {
        String keyword = "ou";
        Mockito.when(customerService.searchCustomers(keyword)).thenReturn(customers);
        mockMvc.perform(get("/api/v1/customers/search?keyword=" + keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(customers)));
    }

    @Test
    void shouldSaveCustomer() throws Exception {
        CustomerDto customer = customers.get(0);
        Mockito.when(customerService.saveCustomer(Mockito.any())).thenReturn(customers.get(0));
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customers.get(0))));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        CustomerDto customer = customers.get(0);
        String id = "c1";
        Mockito.when(customerService.updateCustomer(Mockito.eq(id) ,Mockito.any())).thenReturn(customers.get(0));
        mockMvc.perform(put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customers.get(0))));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        String id = "c1";
        mockMvc.perform(delete("/api/v1/customers/{id}", id))
                .andExpect(status().isNoContent());
    }
}