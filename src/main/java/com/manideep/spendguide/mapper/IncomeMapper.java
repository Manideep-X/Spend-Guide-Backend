package com.manideep.spendguide.mapper;

import org.springframework.stereotype.Component;

import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.entity.CategoryEntity;
import com.manideep.spendguide.entity.IncomeEntity;
import com.manideep.spendguide.entity.ProfileEntity;

// Income Mapper class used for converting DTO to Entity object or vise-versa
// Always returns a new object
@Component
public class IncomeMapper {

    public IncomeEntity dtoToEntity(IncomeDTO incomeDTO, ProfileEntity profileEntity, CategoryEntity categoryEntity) {

        IncomeEntity incomeEntity = new IncomeEntity();

        incomeEntity.setName(incomeDTO.getName());
        incomeEntity.setIconUrl(incomeDTO.getIconUrl());
        incomeEntity.setDate(incomeDTO.getDate());
        incomeEntity.setAmount(incomeDTO.getAmount());
        incomeEntity.setCategoryEntity(categoryEntity);
        incomeEntity.setProfileEntity(profileEntity);

        return incomeEntity;
        
    }
    
    public IncomeDTO entityToDto(IncomeEntity incomeEntity) {

        IncomeDTO incomeDTO = new IncomeDTO();

        incomeDTO.setId(incomeEntity.getId());
        incomeDTO.setName(incomeEntity.getName());
        incomeDTO.setIconUrl(incomeEntity.getIconUrl());
        incomeDTO.setDate(incomeEntity.getDate());
        incomeDTO.setAmount(incomeEntity.getAmount());
        incomeDTO.setCategoryId(
            incomeEntity.getCategoryEntity() != null ? incomeEntity.getCategoryEntity().getId() : null
        );
        incomeDTO.setCategoryName(
            incomeEntity.getCategoryEntity() != null ? incomeEntity.getCategoryEntity().getName() : "New Income"
        );
        incomeDTO.setCreationTime(incomeEntity.getCreationTime());
        incomeDTO.setUpdationTime(incomeEntity.getUpdationTime());

        return incomeDTO;

    }

}
