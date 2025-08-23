package com.manideep.spendguide.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.AuthenticationDTO;
import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.ProfileMapper;
import com.manideep.spendguide.repository.ProfileRepository;
import com.manideep.spendguide.util.JwtUtil;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${app.base-url}")
    private String baseUrl;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper, EmailService emailService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        // this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    // Register new user and send activation link to the registered email
    @Override
    public ProfileDTO registerUserProfile(ProfileDTO profileDTO) throws SQLIntegrityConstraintViolationException {

        ProfileEntity newProfileEntity = profileMapper.dtoToEntity(profileDTO);

        // Generates a unique random ID to activate the user profile and save it in DB.
        if(profileRepository.findByEmail(profileDTO.getEmail()).isPresent()) {
            throw new SQLIntegrityConstraintViolationException("Email already exists! Use another email");
        }
        newProfileEntity.setActivationToken(UUID.randomUUID().toString());
        profileRepository.save(newProfileEntity);

        // Sending activation email to activate the account
        String activationLink = baseUrl + "/api/v1/activate?token=" + newProfileEntity.getActivationToken();
        String subject = "Welcome aboard - Activate Your SpendGuide account";
        String body = "Click on the following link to activate your account: " + activationLink;
        emailService.sendEmail(newProfileEntity.getEmail(), subject, body); // parameters are sendTo,sub,body

        return profileMapper.entityToDto(newProfileEntity);
    }

    // Activate new user account
    @Override
    public boolean activateAccount(String activationToken) {

        // Map every profile(in this case only one as token is unique) to set isActive
        // to true & save it.
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true;
                })
                .orElse(false);

    }

    // Checks if the account is active
    @Override
    public boolean isAccountActive(String email) {
        return profileRepository.findByEmail(email)
                .map(profileEntity -> profileEntity.getIsActive())
                .orElse(false);
    }

    // Returns currently working user account object
    @Override
    public ProfileEntity getCurrentAccount() throws UsernameNotFoundException {

        // SecurityContextHolder is Spring Security’s way of storing details about the currently authenticated user.
        // getContext gets the current SecurityContext.
        // getAuthenticated returns the currently logged-in user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // getName give the username of the user account i.e., email
        return profileRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(
                    "No such account found [Email: " + authentication.getName() + "]"));

    }

    // Returns converted DTO obj of account(from frontend) find by email or logged-in user account
    @Override
    public ProfileDTO getDtoByEmailOrCurrAcc(String email) throws UsernameNotFoundException {

        ProfileEntity profileEntity;
        if (email != null) {
            profileEntity = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                        "No such account found [Email: " + email + "]"));
        }
        else {
            profileEntity = getCurrentAccount();
        }
        return profileMapper.entityToDto(profileEntity);

    }

    // Returns newly generated authentication token along with the user profile
    @Override
    public Map<String, Object> authAndGenerateToken(AuthenticationDTO authDetails) throws UsernameNotFoundException {
        
        try {
            // Authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDetails.getEmail(), authDetails.getPassword()));
            
            // Generate and return JWT token with DTO object
            String jwtToken = jwtUtil.generateToken(authDetails.getEmail());
    
            return Map.of(
                "token", jwtToken,
                "user", getDtoByEmailOrCurrAcc(authDetails.getEmail())
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid credentials: Please check your username & password");
        }


    }

}
