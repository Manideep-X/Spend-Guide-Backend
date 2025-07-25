package com.manideep.spendguide.dto;

import java.time.LocalDateTime;

public class ProfileDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String imageUrl;
    private LocalDateTime creationTime;
    private LocalDateTime updationTime;
    
    public ProfileDTO() {
    }
    public ProfileDTO(Long id, String firstName, String lastName, String email, String password, String imageUrl,
            LocalDateTime creationTime, LocalDateTime updationTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
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

}
