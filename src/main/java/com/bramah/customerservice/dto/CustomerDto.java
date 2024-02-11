package com.bramah.customerservice.dto;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class CustomerDto {


    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
