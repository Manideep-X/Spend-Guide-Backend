package com.manideep.spendguide.service;

import java.util.Map;

import com.manideep.spendguide.dto.AuthenticationDTO;
import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.entity.ProfileEntity;

public interface ProfileService {

    // Add new user profile to the database
    ProfileDTO registerUserProfile(ProfileDTO profileDTO);

    // Activate new user account
    boolean activateAccount(String activationToken);

    // Checks if the account is active
    boolean isAccountActive(String email);

    // Returns currently working user account object
    ProfileEntity getCurrentAccount();

    // Return converted DTO obj of account(from frontend) by email or logged-in user account
    ProfileDTO getDtoByEmailOrCurrAcc(String email);

    // Returns newly generated authentication token along with the user profile
    Map<String, Object> authAndGenerateToken(AuthenticationDTO authDetails);

}
