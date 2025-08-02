package com.manideep.spendguide.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.service.ProfileService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerUserProfile(@RequestBody ProfileDTO profileDTO) {
        
        // This will register new user and create a unique activation token
        ProfileDTO registerProfileDTO = profileService.registerUserProfile(profileDTO);

        // This will send a 201 HTTP status code with the DTO as the response body
        return ResponseEntity.status(HttpStatus.CREATED).body(registerProfileDTO);
    }

}
