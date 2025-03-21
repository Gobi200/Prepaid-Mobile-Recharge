package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Modal.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
//	List<Customer> countByCustomers();
	
//	  Find by city (JPQL Query)
    @Query("SELECT c FROM Customer c WHERE c.city = :city")
    List<Customer> findByCity(@Param("city") String city);
    
    
}
