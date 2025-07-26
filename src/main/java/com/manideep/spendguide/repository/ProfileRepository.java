package com.manideep.spendguide.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manideep.spendguide.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    // Optional is used for explicitly handling null return case (recommended)
    // Internally used query: select * from table_profile where email = ?;
    Optional<ProfileEntity> findByEmail(String email);

}
