package com.bramah.customerservice.web;


import com.bramah.customerservice.dto.CustomerDto;
import com.bramah.customerservice.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable String id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/search")
    public List<CustomerDto> searchCustomers(@RequestParam String keyword) {
        return customerService.searchCustomers(keyword);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return customerService.saveCustomer(customerDto);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        customerService.deleteCustomerById(id);
    }

}
