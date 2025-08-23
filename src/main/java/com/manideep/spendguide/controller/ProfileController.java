package com.manideep.spendguide.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.AuthenticationDTO;
import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.service.ProfileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerUserProfile(@RequestBody ProfileDTO profileDTO) {

        // This will register new user and create a unique activation token
        ProfileDTO registerProfileDTO = null;
        try {
            registerProfileDTO = profileService.registerUserProfile(profileDTO);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(profileDTO);
        }
        // This will send a 201 HTTP status code with the DTO as the response body
        return ResponseEntity.status(HttpStatus.CREATED).body(registerProfileDTO);

    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String activationToken) {

        if (profileService.activateAccount(activationToken)) {
            return ResponseEntity.status(HttpStatus.OK).body("Account is activated successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found or invalid!");

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> postLogin(@RequestBody AuthenticationDTO authDetails) {

        try {
            // Need to check if the user account is activated or not; if not then deny access
            if (!profileService.isAccountActive(authDetails.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "message", "Activation Error: Account is not yet activated."));
            }
            
            // if the account is active then return the user a/c obj and token as response
            Map<String, Object> response = profileService.authAndGenerateToken(authDetails);
            return ResponseEntity.status(HttpStatus.OK).body(response); // same as ResponseEntity.ok(response)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "message", e.getMessage()
            ));
        }

    }

    @GetMapping("/test")
    public String testing() {
        return "JWT based authentication is successfully implemented!";
    }
    

}
