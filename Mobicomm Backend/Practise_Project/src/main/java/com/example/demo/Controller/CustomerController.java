package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Modal.Customer;
import com.example.demo.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping
	public List<Customer> getAllCustomer() {
		return customerService.getAllCustomer();
	}
	
	@GetMapping("{customerId}")
	public Optional<Customer> getCustomerById(@PathVariable Long customerId) {
		return customerService.getCustomerById(customerId);
	}
	
	 @GetMapping("/city/{city}")
	    public List<Customer> getCustomersByCity(@PathVariable String city) {
	        return customerService.getCustomersByCity(city);
	    }
	 
	 @GetMapping("/sort/{field}")
	 public List<Customer> getSortedCustomer(@PathVariable String field){
		 return customerService.getSortedCustomer(field);
	 }
	 
	
	@PostMapping
	public String addNewCustomer(@RequestBody Customer customer) {
		customerService.addNewCustomer(customer);
		return "Customer Added Successfully";
	}
	
	@DeleteMapping("/{customerId}")
	public String deleteCustomer(@PathVariable Long customerId) {
		customerService.deleteCustomer(customerId);
		return "Customer Deleted Successfully";
	}
	
}
