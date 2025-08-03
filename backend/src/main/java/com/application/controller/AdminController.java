package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.application.model.Admin;
import com.application.repository.AdminRepository;

@RestController
public class AdminController {
    
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/loginadmin")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin admin) {
        Admin existingAdmin = adminRepository.findByEmail(admin.getEmail());
        if (existingAdmin != null && existingAdmin.getPassword().equals(admin.getPassword())) {
            return ResponseEntity.ok(existingAdmin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials");
        }
    }
}
