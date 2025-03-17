package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.model.OttPlatform;
import com.example.demo.model.Plan;
import com.example.demo.model.Status;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.OttPlatformRepository;
import com.example.demo.repository.PlanRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanService {
    
    @Autowired
    PlanRepository planRepo;
    
    @Autowired
    CategoryRepository categoryRepo;
    
    @Autowired
    OttPlatformRepository ottRepo;
    
    private String generatePlanId() {
        Plan lastPlan = planRepo.findTopByOrderByPlanIdDesc();
        
        if (lastPlan != null && lastPlan.getPlanId() != null) {
            String lastId = lastPlan.getPlanId();
            int lastNumber = Integer.parseInt(lastId.substring(4)); // Extract numeric part after "MBP_"
            int newNumber = lastNumber + 1;
            
            return String.format("MBP_%04d", newNumber); // Pad with leading zeros to 4 digits
        } 
        else {
            return "MBP_0001"; // Initial ID with 4-digit padding
        }
    }

    public List<Plan> getPlans() {
        return planRepo.findAll();
    }
    
    public Optional<Plan> getPlanById(String id) {
		return planRepo.findById(id);
	}

//    public List<Plan> getActivePlans() {
//        return planRepo.findByStatus("Active");
//    }
    public List<Plan> getActivePlans() {
        return planRepo.findByStatus(Status.ACTIVE); // ✅ Correct Enum Usage
    }
    
    public List<Plan> getInactivePlans() {
        return planRepo.findByStatus(Status.INACTIVE); // ✅ Correct Enum Usage
    }


    public String addPlan(Plan plan) {
        plan.setPlanId(generatePlanId());

        if (plan.getStatus() == null) {
            plan.setStatus(Status.ACTIVE);
        }

        // ✅ Fetch and set the category
        if (plan.getCategory() != null && plan.getCategory().getCategoryId() != null) {
            Category category = categoryRepo.findById(plan.getCategory().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found!"));
            plan.setCategory(category);
        } else {
            throw new RuntimeException("Category is required!");
        }

        // ✅ Fetch existing OTT platforms instead of using request data directly
        if (plan.getOttPlans() != null && !plan.getOttPlans().isEmpty()) {
            Set<OttPlatform> existingOtts = new HashSet<>();
            for (OttPlatform ott : plan.getOttPlans()) {
                OttPlatform existingOtt = ottRepo.findById(ott.getOttId())
                        .orElseThrow(() -> new RuntimeException("OTT Platform not found: " + ott.getOttId()));
                existingOtts.add(existingOtt);
            }
            plan.setOttPlans(existingOtts); // ✅ Associate with existing OTTs
        }

        planRepo.save(plan);
        return "Plan Added Successfully!";
    }

    public String deletePlan(String id) {
        if (!planRepo.existsById(id)) {
            throw new RuntimeException("Plan Id Doesn't Exist!");
        }
        planRepo.deleteById(id);
        return "Plan Id " + id + " deleted Successfully!";
    }

    public void updatePlan(String id, Plan plan) {
        Optional<Plan> existingPlanOpt = planRepo.findById(id);
        if (!existingPlanOpt.isPresent()) {
            throw new RuntimeException("Plan Id Doesn't Exist!");
        }
        Plan existingPlan = existingPlanOpt.get();
        
        existingPlan.setPlanPrice(plan.getPlanPrice());
        existingPlan.setVoice(plan.getVoice());
        existingPlan.setValidity(plan.getValidity());
        existingPlan.setData(plan.getData());
        existingPlan.setMessage(plan.getMessage());
        existingPlan.setTag(plan.getTag());
        existingPlan.setDescription(plan.getDescription());
        existingPlan.setCategory(plan.getCategory());
        existingPlan.setOttPlans(plan.getOttPlans());
        planRepo.save(existingPlan);
    }

    public void activatePlan(String planId) {
        Optional<Plan> optionalPlan = planRepo.findById(planId);
        if (optionalPlan.isPresent()) {
            Plan plan = optionalPlan.get();
            plan.setStatus(Status.ACTIVE);
            planRepo.save(plan);
        } else {
            throw new RuntimeException("Plan not found with ID: " + planId);
        }
    }
    
    public void deactivatePlan(String planId) {
        Optional<Plan> optionalPlan = planRepo.findById(planId);
        if (optionalPlan.isPresent()) {
            Plan plan = optionalPlan.get();
            plan.setStatus(Status.INACTIVE);
            planRepo.save(plan);
        } else {
            throw new RuntimeException("Plan not found with ID: " + planId);
        }
    }

    @Transactional
    public List<Plan> getPlansByCategory(String categoryId) {
        List<Plan> plans = planRepo.findByCategory_CategoryIdAndStatus(categoryId, Status.ACTIVE);
        plans.forEach(plan -> plan.getOttPlans().size());  // Initialize ottPlans
        return plans;
    }
}
