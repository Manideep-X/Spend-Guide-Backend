package com.manideep.spendguide.mapper;

import org.springframework.stereotype.Component;

import com.manideep.spendguide.dto.CategoryDTO;
import com.manideep.spendguide.entity.CategoryEntity;
import com.manideep.spendguide.entity.ProfileEntity;

// Category Mapper class used for converting DTO to Entity object or vise-versa
// Always returns a new object
@Component
public class CategoryMapper {

    public CategoryEntity dtoToEntity(CategoryDTO categoryDTO, ProfileEntity profileEntity) {
        
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setIconUrl(categoryDTO.getIconUrl());
        categoryEntity.setType(categoryDTO.getType());
        categoryEntity.setProfile(profileEntity);

        return categoryEntity;

    }

    public CategoryDTO entityToDto(CategoryEntity categoryEntity) {

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setProfileId(
            categoryEntity.getProfile() != null ? categoryEntity.getProfile().getId() : null
        );
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setCreationTime(categoryEntity.getCreationTime());
        categoryDTO.setUpdationTime(categoryEntity.getUpdationTime());
        categoryDTO.setIconUrl(categoryEntity.getIconUrl());
        categoryDTO.setType(categoryEntity.getType());

        return categoryDTO;

    }

}
