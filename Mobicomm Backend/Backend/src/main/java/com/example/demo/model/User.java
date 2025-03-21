package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "mobile", unique = true, nullable = false, length = 15)
    private String mobile;

    @Column(name = "aadhar_no", unique = true, nullable = false, length = 12)
    private String aadharNo;

    @Column(name = "dob", nullable = false, length = 10)
    private String dob;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "pincode", length = 10)
    private String pincode;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", length = 50)
    private String country;
}
