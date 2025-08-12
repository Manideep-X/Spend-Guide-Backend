package com.manideep.spendguide.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IncomeDTO {

    private Long id;
    private String name;
    private String iconUrl;
    private LocalDate date;
    private BigDecimal amount;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime creationTime;
    private LocalDateTime updationTime;
    
    public IncomeDTO() {
    }

    public IncomeDTO(Long id, String name, String iconUrl, LocalDate date, BigDecimal amount, Long categoryId,
            String categoryName, LocalDateTime creationTime, LocalDateTime updationTime) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
