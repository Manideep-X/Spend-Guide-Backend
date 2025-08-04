package com.manideep.spendguide.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.entity.ProfileEntity;

// Mapper class used for converting DTO to Entity object or vise-versa
// Always returns a new object
@Component
public class ProfileMapper {

    private final PasswordEncoder passwordEncoder;
    
    public ProfileMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileEntity dtoToEntity(ProfileDTO profileDTO) {
        
        ProfileEntity profileEntity = new ProfileEntity();
        
        profileEntity.setId(profileDTO.getId());
        profileEntity.setFirstName(profileDTO.getFirstName());
        profileEntity.setLastName(profileDTO.getLastName());
        profileEntity.setEmail(profileDTO.getEmail());
        profileEntity.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
        profileEntity.setCreationTime(profileDTO.getCreationTime());
        profileEntity.setUpdationTime(profileDTO.getUpdationTime());
    
        return profileEntity;
    }

    public ProfileDTO entityToDto(ProfileEntity profileEntity) {
        
        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId(profileEntity.getId());
        profileDTO.setFirstName(profileEntity.getFirstName());
        profileDTO.setLastName(profileEntity.getLastName());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setPassword(profileEntity.getPassword());
        profileDTO.setCreationTime(profileEntity.getCreationTime());
        profileDTO.setUpdationTime(profileEntity.getUpdationTime());

        return profileDTO;
    }

}
