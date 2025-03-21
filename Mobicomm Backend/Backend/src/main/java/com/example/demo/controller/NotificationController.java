package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EmailService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService; // Make sure you have an email service

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String username = request.get("username");
        String expiryDate = request.get("expiryDate");

        String subject = "Plan Expiry Reminder";
        String body = "Dear " + username + ",\n\n" +
                      "Your prepaid plan will expire on " + expiryDate + ". " +
                      "Please recharge soon to avoid service interruption.\n\n" +
                      "Thank you,\nMobiComm Team";

        emailService.sendEmail(email, subject, body);
        return ResponseEntity.ok("Email sent successfully");
    }
}
