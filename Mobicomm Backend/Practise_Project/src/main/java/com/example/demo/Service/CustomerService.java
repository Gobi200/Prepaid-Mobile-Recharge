package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.Modal.Customer;
import com.example.demo.Repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;

	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	public void addNewCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void deleteCustomer(Long customerId) {
		customerRepository.deleteById(customerId);
	}

	public Optional<Customer> getCustomerById(Long customerId) {
		return customerRepository.findById(customerId);
		
	}

	public List<Customer> getCustomersByCity(String city) {
		return customerRepository.findByCity(city);
	}
	
	public List<Customer> getSortedCustomer(String field){
		return customerRepository.findAll(Sort.by(Direction.ASC, field));
	}


	
}
