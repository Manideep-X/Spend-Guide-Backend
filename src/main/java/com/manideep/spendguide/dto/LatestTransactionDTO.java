package com.manideep.spendguide.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LatestTransactionDTO {

    private Long id;
    private Long profileId;
    private String iconUrl;
    private String name;
    private BigDecimal amount;
    private LocalDate date;
    private String type;
    private LocalDateTime creationTime;
    private LocalDateTime updationTime;
    
    public LatestTransactionDTO() {
    }
    public LatestTransactionDTO(Long id, Long profileId, String iconUrl, String name, BigDecimal amount, LocalDate date,
            String type, LocalDateTime creationTime, LocalDateTime updationTime) {
        this.id = id;
        this.profileId = profileId;
        this.iconUrl = iconUrl;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
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
    
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
