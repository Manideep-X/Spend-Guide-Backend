package com.manideep.spendguide.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.ProfileMapper;
import com.manideep.spendguide.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper, EmailService emailService) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.emailService = emailService;
    }

    // Register new user and send activation link to the registered email
    public ProfileDTO registerUserProfile(ProfileDTO profileDTO) {
        
        ProfileEntity newProfileEntity = profileMapper.dtoToEntity(profileDTO);
        
        // Generates a unique random ID to activate the user profile and save it in DB.
        newProfileEntity.setActivationToken(UUID.randomUUID().toString()); 
        profileRepository.save(newProfileEntity);

        // Sending activation email to activate the account
        String activationLink = baseUrl+"/api/v1/activate?token="+newProfileEntity.getActivationToken();
        String subject = "Welcome aboard - Activate Your SpendGuide account";
        String body = "Click on the following link to activate your account: "+activationLink;
        emailService.sendEmail(newProfileEntity.getEmail(), subject, body); // parameters are sendTo,sub,body
        
        return profileMapper.entityToDto(newProfileEntity);
    }

    // Activate new user account
    public boolean activateAccount(String activationToken) {

        // Map every profile(in this case only one as token is unique) to set isActive to true & save it.
        return profileRepository.findByActivationToken(activationToken)
        .map(profile -> {
            profile.setIsActive(true);
            profileRepository.save(profile);
            return true;
        })
        .orElse(false);

    }
    

}
