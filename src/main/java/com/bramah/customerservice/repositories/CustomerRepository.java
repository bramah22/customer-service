package com.bramah.customerservice.repositories;

import com.bramah.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    public Optional<Customer> findByEmail(String email);
    List<Customer> findByFirstNameContainsIgnoreCase(String keyword);
}
