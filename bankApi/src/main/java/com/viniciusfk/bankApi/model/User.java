package com.viniciusfk.bankApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@Entity
@Table(name = "userTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must have atleast 3 digits.")
    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    @NotBlank(message = "CPF is required.")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "Please insert a password.")
    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @NotBlank(message = "Phone number is required.")
    @Column(name = "phone", length = 15, nullable = false, unique = true)
    private String phone;

    @NumberFormat
    @Column(name = "balance")
    private double balance;
}