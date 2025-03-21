package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Plan;
import com.example.demo.model.Status;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {
    Plan findTopByOrderByPlanIdDesc();
    
    List<Plan> findByStatus(Status active);

	List<Plan> findByCategory_CategoryIdAndStatus(String categoryId, Status active);
	
	@Query("SELECT p.planPrice FROM Plan p WHERE p.planId = :planId")
    String findPlanPriceById(@Param("planId") String planId);

}

