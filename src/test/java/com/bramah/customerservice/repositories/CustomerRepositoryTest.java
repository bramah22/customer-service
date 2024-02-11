package com.bramah.customerservice.repositories;

import com.bramah.customerservice.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFindCustomerByEmail() {
        // Given
        String givenEmail = "noufala@gmail.com";

        // When
        Optional<Customer> result = customerRepository.findByEmail(givenEmail);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(givenEmail);
    }

    @Test
    void shouldNotFindCustomerByEmail() {

        String givenEmail = "xxx@xxxxxxx.com";

        Optional<Customer> result = customerRepository.findByEmail(givenEmail);
        assertThat(result).isEmpty();
    }
    @Test
    void shouldFindCustomerByFirstNameOk() {

        String keyword = "ou";
        List<Customer> expected = List.of(
                Customer.builder().firstName("Noufala").lastName("MOUNPAIN").email("noufala@gmail.com").build(),
                Customer.builder().firstName("Soumi").lastName("MANDOU").email("soumi@gmail.com").build()
        );

        List<Customer> result = customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);

    }

}