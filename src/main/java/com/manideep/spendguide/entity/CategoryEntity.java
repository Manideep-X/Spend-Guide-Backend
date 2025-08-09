package com.manideep.spendguide.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "table_category",
    uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "name"})
)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column(name = "updation_time")
    @UpdateTimestamp
    private LocalDateTime updationTime;

    @Column(name = "icon_url")
    private String iconUrl;

    private String type; // income/expense type

    // ManyToOne is preffered here as work mostly needed to be done from category not from profile obj.
    // OneToMany is not used as fetching profile obj and then categories can increase overhead.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;

    public CategoryEntity() {
    }
    public CategoryEntity(Long id, String name, LocalDateTime creationTime, LocalDateTime updationTime, String iconUrl, String type, ProfileEntity profile) {
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
        this.iconUrl = iconUrl;
        this.type = type;
        this.profile = profile;
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

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public ProfileEntity getProfile() {
        return profile;
    }
    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

}
