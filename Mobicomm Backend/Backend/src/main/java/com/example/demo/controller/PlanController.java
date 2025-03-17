package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Plan;
import com.example.demo.service.PlanService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/plans")
public class PlanController {
	
	@Autowired
	PlanService planService;
	
	@GetMapping
	public List<Plan> getPlans(){
		return planService.getPlans();
	}
	
	@GetMapping("{id}")
	public Optional<Plan> getPlanById(@PathVariable String id){
		return planService.getPlanById(id);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Plan>> getPlansByCategory(@PathVariable String categoryId) {
	    List<Plan> plans = planService.getPlansByCategory(categoryId);
	    
	    // Force fetch OTT platforms
	    plans.forEach(plan -> plan.getOttPlans().size()); 
	    return ResponseEntity.ok(plans);
	}

	 
	@PostMapping
	public String addPlan(@RequestBody Plan plan) {
		return planService.addPlan(plan);
	}
	
	@PutMapping("/{id}")
	public String updatePlan(@PathVariable String id, @RequestBody Plan plan) {
		planService.updatePlan(id,plan);
		return "Plan "+id+" Updated successfully";
	}
	
	
	@PutMapping("/{id}/active")
	public String activatePlan(@PathVariable String id) {
		planService.activatePlan(id);
		return "Plan "+id+" Activated Successfully";
	}
	
	@PutMapping("/{id}/inactive")
	public String deactivatePlan(@PathVariable String id) {
		planService.deactivatePlan(id);
		return "Plan "+id+" Deactivated Successfully";
	}
	
	@DeleteMapping("/{id}")
	public String deletePlan(@PathVariable String id) {
		return planService.deletePlan(id);
	}
}
