package com.manideep.spendguide.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(unique = true)
    private String email;
    
    private String password;

    @Column(name = "image_url")
    private String imageUrl;
    
    // updatable=false is used to not update this field while executing any update query.
    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp // will create timestamp only once
    private LocalDateTime creationTime;
    
    @Column(name = "updation_time")
    @UpdateTimestamp // will update timestamp on every update query
    private LocalDateTime updationTime;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "activation_token")
    private String activationToken;

    public ProfileEntity() {
    }
    public ProfileEntity(Long id, String firstName, String lastName, String email, String password, String imageUrl,
            LocalDateTime creationTime, LocalDateTime updationTime, Boolean isActive, String activationToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
        this.isActive = isActive;
        this.activationToken = activationToken;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getUpdationTime() {
        return updationTime;
    }
    public void setUpdationTime(LocalDateTime updationTime) {
        this.updationTime = updationTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getActivationToken() {
        return activationToken;
    }
    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    // By default the isActive should be false, and will only be set true after verifying the email.
    // @PrePersist is used to execute the method before saving the current working object into the DB.
    @PrePersist
    public void isActivePerPersist() {
        if (isActive == null) {
            isActive = false;
        }
    }

}
