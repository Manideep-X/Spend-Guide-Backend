package com.manideep.spendguide.service;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    
    

}
