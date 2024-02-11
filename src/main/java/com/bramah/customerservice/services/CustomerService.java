package com.bramah.customerservice.services;

import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.exceptions.CustomerNotFoundException;
import com.bramah.customerservice.exceptions.EmailAlreadyExistException;

import java.util.List;

public interface CustomerService {


    CustomerDto findCustomerById(String id) throws CustomerNotFoundException;
    CustomerDto saveCustomer(CustomerDto customerDto) throws EmailAlreadyExistException;
    List<CustomerDto> getAllCustomers();
    List<CustomerDto> searchCustomers(String keyword);
    CustomerDto updateCustomer(String id, CustomerDto customerDto) throws CustomerNotFoundException;
    void deleteCustomerById(String id) throws CustomerNotFoundException;



}
