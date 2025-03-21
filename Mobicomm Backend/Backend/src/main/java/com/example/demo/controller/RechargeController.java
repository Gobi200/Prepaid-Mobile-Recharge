package com.example.demo.controller;

import com.example.demo.model.Plan;
import com.example.demo.model.Recharge;
import com.example.demo.model.User;
import com.example.demo.repository.RechargeRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.RechargeService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/recharge")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;
   
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RechargeRepository rechargeRepository;

    @GetMapping
    public ResponseEntity<List<Recharge>> getAllRecharges() {
        List<Recharge> recharges = rechargeService.getAllRecharges();
        return ResponseEntity.ok(recharges);
    }
    
    @GetMapping("/expiring")
    public List<Recharge> getExpiringRecharges() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeDaysLater = currentDate.plusDays(3);

        return rechargeRepository.findByExpiryDateBetween(currentDate, threeDaysLater);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recharge>> getUserRecharges(@PathVariable String userId) {
        List<Recharge> recharges = rechargeService.findByUserUserId(userId);
        return ResponseEntity.ok(recharges);
    }


    
    @PostMapping
    public ResponseEntity<?> saveRecharge(@RequestBody Recharge recharge) {
        Recharge savedRecharge = rechargeService.saveRecharge(recharge);

        // Get the correct user details
        User user = savedRecharge.getUser();
        String userEmail = user.getEmail();
        String userName = user.getUsername();

        // Get the plan details
        Plan plan = savedRecharge.getPlan();

        // Construct HTML email body
        String subject = "Plan Recharged Successfully! - MobiComm";

        String body = "<!DOCTYPE html>" +
                      "<html>" +
                      "<head>" +
                      "<style>" +
                      "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                      ".container { max-width: 600px; background: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); margin: auto; }" +
                      ".header { background: linear-gradient(45deg, #007bff, #00d4ff); color: #ffffff; text-align: center; padding: 15px; font-size: 22px; font-weight: bold; border-radius: 10px 10px 0 0; }" +
                      ".content { padding: 20px; color: #333333; font-size: 16px; line-height: 1.6; }" +
                      ".details { background: #f9f9f9; padding: 15px; border-radius: 8px; font-size: 14px; border-left: 5px solid #007bff; }" +
                      ".details p { margin: 8px 0; }" +
                      ".cta { text-align: center; margin-top: 20px; }" +
                      ".cta a { background: #007bff; color: #ffffff; padding: 12px 20px; text-decoration: none; font-weight: bold; border-radius: 5px; display: inline-block; }" +
                      ".footer { text-align: center; font-size: 12px; color: #777777; padding: 15px; margin-top: 20px; border-top: 1px solid #eeeeee; }" +
                      "</style>" +
                      "</head>" +
                      "<body>" +
                      "<div class='container'>" +
                      "<div class='header'>🎉 Recharge Confirmation</div>" +
                      "<div class='content'>" +
                      "<p>Dear <strong>" + userName + "</strong>,</p>" +
                      "<p>Thank you for choosing <strong>MobiComm</strong>! Your mobile recharge plan has been successfully activated.</p>" +
                      "<div class='details'>" +
                      "<p>📆 <strong>Validity:</strong> " + plan.getValidity() + "</p>" +
                      "<p>🌐 <strong>Data:</strong> " + plan.getData() + "</p>" +
                      "<p>📞 <strong>Voice:</strong> " + plan.getVoice() + "</p>" +
                      "<p>💬 <strong>Messages:</strong> " + plan.getMessage() + "</p>" +
                      "<p>💰 <strong>Price:</strong> ₹" + plan.getPlanPrice() + "</p>" +
                      "<p>ℹ️ <strong>Description:</strong> " + plan.getDescription() + "</p>" +
                      "</div>" +
                      "<p><strong>Transaction ID:</strong> " + savedRecharge.getPaymentId() + "</p>" +
                      "<div class='cta'>" +
                      "<a href='mailto:mobicomm.service@gmail.com'>📩 Contact Support</a>" +
                      "</div>" +
                      "</div>" +
                      "<div class='footer'>© 2025 MobiComm. All rights reserved.</div>" +
                      "</div>" +
                      "</body>" +
                      "</html>";

        emailService.sendPurchaseEmail(userEmail, subject, body); // Ensure HTML format

        return ResponseEntity.ok(savedRecharge);
    }
    

}