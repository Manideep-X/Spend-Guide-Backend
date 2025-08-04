package com.manideep.spendguide.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.repository.ProfileRepository;

import io.jsonwebtoken.lang.Collections;

// This class will tell Spring Security how to load user data from DB during login/authentication.
@Service
public class TheUserDetailService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public TheUserDetailService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ProfileEntity profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such account found [Email: " + email + "]"));

        return User.builder()
                .username(email)
                .password(profile.getPassword())
                .authorities(Collections.emptyList()) // This is empty because there is no role assigned
                .build();

    }

}
