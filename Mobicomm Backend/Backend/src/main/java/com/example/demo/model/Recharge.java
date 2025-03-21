package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recharge {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rechargeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensure it correctly maps to User
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false) // Ensure it correctly maps to Plan
    private Plan plan;

    private String transactionStatus;
    private String paymentId;
    
    private LocalDateTime rechargeDate;
    
    private LocalDateTime expiryDate;
   
}
