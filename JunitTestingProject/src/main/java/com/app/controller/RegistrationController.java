package com.app.controller;

import com.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RegistrationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendOTP")
    public ResponseEntity<String> sendOTP(@RequestBody String email) {
        // Generate and send OTP via email
        String otp = generateOTP();
        emailService.sendOTP(email, otp);
        return ResponseEntity.ok("OTP sent successfully");
    }


    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        return String.valueOf(otpNumber);
    }
}
