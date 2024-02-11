package com.bramah.customerservice.services.impl;

import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.entities.Customer;
import com.bramah.customerservice.exceptions.CustomerNotFoundException;
import com.bramah.customerservice.exceptions.EmailAlreadyExistException;
import com.bramah.customerservice.mappers.CustomerMapper;
import com.bramah.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks  // Les inejecter les mocks (customerService et CustomerMapper)
    private CustomerServiceImpl underTest;


    @Test
    void shouldSaveNewCustomer() {
        // Given
        CustomerDto customerDto = CustomerDto.builder().firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Customer customer = Customer.builder().firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Customer savedCustomer = Customer.builder().id("c1").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        CustomerDto expected = CustomerDto.builder().id("c1").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Mockito.when(customerRepository.findByEmail(customerDto.getEmail())).thenReturn(Optional.empty());
        Mockito.when(customerMapper.toEntity(customerDto)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCustomer);
        Mockito.when(customerMapper.toDto(savedCustomer)).thenReturn(expected);

        // When
        CustomerDto result = underTest.saveCustomer(customerDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    void shouldNotSaveNewCustomerWhenEmailExist() {
        // Given
        CustomerDto customerDto = CustomerDto.builder().firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Customer customer = Customer.builder().firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Mockito.when(customerRepository.findByEmail(customerDto.getEmail())).thenReturn(Optional.of(customer));
        assertThatThrownBy(() -> underTest.saveCustomer(customerDto))
                .isInstanceOf(EmailAlreadyExistException.class);
    }

    @Test
    void shouldGetAllCustomers() {
        // Given
        List<Customer> customers = List.of(
                Customer.builder().id("c1").firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build(),
                Customer.builder().id("c2").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build()
        );

        List<CustomerDto> expected = List.of(
                CustomerDto.builder().id("c1").firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build(),
                CustomerDto.builder().id("c2").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build()
        );

        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(customerMapper.toDto(customers)).thenReturn(expected);
        List<CustomerDto> result = underTest.getAllCustomers();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldFindCustomerById() {
        // Given
        String customerId = "c1";
        Customer customer = Customer.builder().id("c1").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        CustomerDto expected = CustomerDto.builder().id("c1").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.toDto(customer)).thenReturn(expected);

        // when
        CustomerDto result = underTest.findCustomerById(customerId);

        //Then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenFindById() {
        // Given
        String customerId = "c1";
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findCustomerById(customerId))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(null);
    }


    @Test
    void shouldSearchCustomers() {
        // Given
        String keyword = "ou";
        List<Customer> customers = List.of(
                Customer.builder().id("c1").firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build(),
                Customer.builder().id("c2").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build()
        );

        List<CustomerDto> expected = List.of(
                CustomerDto.builder().id("c1").firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build(),
                CustomerDto.builder().id("c2").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build()
        );

        Mockito.when(customerRepository.findByFirstNameContainsIgnoreCase(keyword)).thenReturn(customers);
        Mockito.when(customerMapper.toDto(customers)).thenReturn(expected);
        List<CustomerDto> result = underTest.searchCustomers(keyword);
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void shouldDeleteCustomer() {
        // Given
        String customerId = "c1";
        Customer customer = Customer.builder().id("c1").firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        underTest.deleteCustomerById(customerId);

        //Then
        Mockito.verify(customerRepository).deleteById(customerId);
    }

    @Test
    void shouldNotDeleteCustomerIfNotExist() {
        // Given
        String customerId = "c1";
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.deleteCustomerById(customerId))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(null);
    }
}