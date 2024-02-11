package com.bramah.customerservice.mappers;

import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.entities.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class CustomerMapperTest {

    CustomerMapper underTest = new CustomerMapper();

    @Test
    void shouldMapCustomerToCustomerDto() {

        // Given
        Customer givenCustomer = Customer.builder().id("c1").firstName("Ibrahima").lastName("NJUTAPMVOUI").email("bramah22@gmail.com").build();
        CustomerDto expected = CustomerDto.builder().id("c1").firstName("Ibrahima").lastName("NJUTAPMVOUI").email("bramah22@gmail.com").build();

        //When
        CustomerDto result = underTest.toDto(givenCustomer);

        //then
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapCustomerDtoToCustomer() {

        // Given
        CustomerDto givenCustomer = CustomerDto.builder().id("c1").firstName("Ibrahima").lastName("NJUTAPMVOUI").email("bramah22@gmail.com").build();
        Customer expected = Customer.builder().id("c1").firstName("Ibrahima").lastName("NJUTAPMVOUI").email("bramah22@gmail.com").build();

        //When
        Customer result = underTest.toEntity(givenCustomer);

        //then
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapListOfCustomersToListCustomerDtos() {

        // Given
        List<Customer> customers = List.of(
                Customer.builder().id("c1").firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build(),
                Customer.builder().id("c2").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build()
        );

        List<CustomerDto> expected = List.of(
                CustomerDto.builder().id("c1").firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build(),
                CustomerDto.builder().id("c2").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build()
        );

        //When
        List<CustomerDto> result = underTest.toDto(customers);

        //then
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    void shouldNotMapNullCustomerToCustomerDto() {

        // Given
        Customer givenCustomer = null;
        //When
        assertThatThrownBy(() -> underTest.toDto(givenCustomer)).isInstanceOf(IllegalArgumentException.class);

    }

}