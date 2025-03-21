package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Plan;
import com.example.demo.model.Recharge;
import com.example.demo.model.User;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RechargeRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RechargeService {

    @Autowired
    private RechargeRepository rechargeRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PlanRepository planRepository;
    
    public List<Recharge> getAllRecharges() {
        return rechargeRepository.findAll();
    }
    
    public List<Recharge> getExpiringRecharges(LocalDateTime expiryLimit) {
        return rechargeRepository.findExpiringRecharges(expiryLimit);
    }
    
    public Recharge saveRecharge(Recharge recharge) {
        if (recharge.getUser() == null || recharge.getPlan() == null) {
            throw new IllegalArgumentException("User ID or Plan ID is missing.");
        }

        // Fetch User and Plan from the database using IDs
        User user = userRepository.findById(recharge.getUser().getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        Plan plan = planRepository.findById(recharge.getPlan().getPlanId())
                                  .orElseThrow(() -> new RuntimeException("Plan not found"));

        // Set the fetched user and plan in Recharge object
        recharge.setUser(user);
        recharge.setPlan(plan);

        return rechargeRepository.save(recharge);
    }

    public List<Recharge> findByUserUserId(String userId) {
        return rechargeRepository.findByUserUserId(userId);
    }
    
}
