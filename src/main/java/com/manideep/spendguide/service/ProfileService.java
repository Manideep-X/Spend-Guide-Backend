package com.manideep.spendguide.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.ProfileMapper;
import com.manideep.spendguide.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public ProfileDTO registerUserProfile(ProfileDTO profileDTO) {
        
        ProfileEntity newProfileEntity = profileMapper.dtoToEntity(profileDTO);
        
        // Generates a unique random ID to activate the user profile and save it in DB.
        newProfileEntity.setActivationToken(UUID.randomUUID().toString()); 
        profileRepository.save(newProfileEntity);
        
        return profileMapper.entityToDto(newProfileEntity);
    }
    

}
