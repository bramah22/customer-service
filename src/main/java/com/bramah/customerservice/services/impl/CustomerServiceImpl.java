package com.bramah.customerservice.services.impl;

import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.entities.Customer;
import com.bramah.customerservice.exceptions.CustomerNotFoundException;
import com.bramah.customerservice.exceptions.EmailAlreadyExistException;
import com.bramah.customerservice.mappers.CustomerMapper;
import com.bramah.customerservice.repositories.CustomerRepository;
import com.bramah.customerservice.services.CustomerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public CustomerDto findCustomerById(String id) throws CustomerNotFoundException{
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            logCustomerNotFound(id);
            throw new CustomerNotFoundException();
        }
        return customerMapper.toDto(customer.get());
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) throws EmailAlreadyExistException {
        log.info("Saving new customer : {} ", customerDto.toString());
        Optional<Customer> byEmail = customerRepository.findByEmail(customerDto.getEmail());

        if (byEmail.isPresent()) {
            log.error("This email {} is already exist", customerDto.getEmail());
            throw new EmailAlreadyExistException();
        }
        Customer customer = customerMapper.toEntity(customerDto);
        Customer customerSaved = customerRepository.save(customer);
        return customerMapper.toDto(customerSaved);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> all = customerRepository.findAll();
        return customerMapper.toDto(all);
    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        return customerMapper.toDto(customers);
    }

    @Override
    public CustomerDto updateCustomer(String id, CustomerDto customerDto) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            logCustomerNotFound(id);
            throw new CustomerNotFoundException();
        }
        customerDto.setId(id);
        Customer customerToUpdate = customerMapper.toEntity(customerDto);
        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        return customerMapper.toDto(updatedCustomer);
    }

    @Override
    public void deleteCustomerById(String id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            logCustomerNotFound(id);
            throw new CustomerNotFoundException();
        }
        customerRepository.deleteById(id);
    }

    private static void logCustomerNotFound(String id) {
        log.error("Customer with id: {} not found", id);
    }
}
