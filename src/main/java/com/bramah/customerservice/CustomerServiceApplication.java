package com.bramah.customerservice;

import com.bramah.customerservice.entities.Customer;
import com.bramah.customerservice.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.xml.stream.events.Comment;

@SpringBootApplication
@Slf4j
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}


	@Bean
	@Profile("!test")
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		log.info("j'execute le main.....");
		return args -> {
			customerRepository.save(Customer.builder().firstName("Ibrahima").lastName("NJUTAPMVOUI").email("bramah22@gmail.com").build());
			customerRepository.save(Customer.builder().firstName("Noufala").lastName("Mounpain").email("noufala@gmail.com").build());
			customerRepository.save(Customer.builder().firstName("Soumi").lastName("Mandou").email("mandou@gmail.com").build());
		};
	}
}
