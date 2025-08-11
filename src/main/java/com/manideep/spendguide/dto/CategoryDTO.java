package com.manideep.spendguide.dto;

import java.time.LocalDateTime;

public class CategoryDTO {

    private Long id;
    private Long profileId;
    private String name;
    private LocalDateTime creationTime;
    private LocalDateTime updationTime;
    private String iconUrl;
    private String type; // income/expense type
    
    public CategoryDTO() {
    }
    public CategoryDTO(Long id, Long profileId, String name, LocalDateTime creationTime, LocalDateTime updationTime,
            String iconUrl, String type) {
        this.id = id;
        this.profileId = profileId;
        this.name = name;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
        this.iconUrl = iconUrl;
        this.type = type;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
