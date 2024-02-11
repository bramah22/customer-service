package com.bramah.customerservice.mappers;


import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CustomerDto toDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }
    public Customer toEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }

    public List<Customer> toEntity(List<CustomerDto> customerDtoList) {
        return customerDtoList.stream()
                .map(customerDto -> modelMapper.map(customerDto, Customer.class))
                .toList();
    }
    public List<CustomerDto> toDto(List<Customer> customerList) {
        return customerList.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .toList();
    }
}
