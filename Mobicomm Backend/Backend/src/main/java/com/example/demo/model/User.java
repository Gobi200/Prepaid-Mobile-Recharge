package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private String userid;
	private String username;
	private String email;
	private String mobile;
	private String aadharNo;
	private String Dob;
	private String address;
	private String city;
	private String pincode;
	private String state;
	private String country;
}
