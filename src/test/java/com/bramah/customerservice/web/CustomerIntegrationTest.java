package com.bramah.customerservice.web;

import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.repositories.CustomerRepository;
import com.bramah.customerservice.security.SecurityInsecureConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


// @Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Import(SecurityInsecureConfig.class)
class CustomerIntegrationTest {


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    //@Container
    //@ServiceConnection
    // private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16");

    List<CustomerDto> customers;

    @BeforeEach
    void setUp() {
        this.customers = new ArrayList<>();
        this.customers.add(CustomerDto.builder().id("c1").firstName("Ibrahima").lastName("NJUTAPMVOUI").email("bramah@gmail.com").build());
        this.customers.add(CustomerDto.builder().id("c2").firstName("Noufala").lastName("MOUNPAIN").email("noufala@gmail.com").build());
        this.customers.add(CustomerDto.builder().id("c3").firstName("Soumi").lastName("MANDOU").email("soumi@gmail.com").build());
        //customerRepository.saveAll(customers);
    }


    @Test
    void shouldGetAllCustomers() {
        // When
        ResponseEntity<CustomerDto[]> response = testRestTemplate.exchange("/api/v1/customers", HttpMethod.GET, null, CustomerDto[].class);
        List<CustomerDto> content = Arrays.asList(Objects.requireNonNull(response.getBody()));

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(content).hasSize(3);
        // assertThat(content).usingRecursiveComparison().ignoringCollectionOrder().ignoringFields("id").isEqualTo(customers);
        Assertions.assertThat(content)
                .map(CustomerDto::getFirstName, CustomerDto::getLastName, CustomerDto::getEmail)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(customers.get(0).getFirstName(),customers.get(0).getLastName(),customers.get(0).getEmail()),
                        Tuple.tuple(customers.get(1).getFirstName(),customers.get(1).getLastName(),customers.get(1).getEmail()),
                        Tuple.tuple(customers.get(2).getFirstName(),customers.get(2).getLastName(),customers.get(2).getEmail())
                );

    }

}
