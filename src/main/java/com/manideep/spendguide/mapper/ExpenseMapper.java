package com.manideep.spendguide.mapper;

import org.springframework.stereotype.Component;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.entity.CategoryEntity;
import com.manideep.spendguide.entity.ExpenseEntity;
import com.manideep.spendguide.entity.ProfileEntity;

// Expense Mapper class used for converting DTO to Entity object or vise-versa
// Always returns a new object
@Component
public class ExpenseMapper {

    public ExpenseEntity dtoToEntity(ExpenseDTO expenseDTO, ProfileEntity profileEntity, CategoryEntity categoryEntity) {

        ExpenseEntity expenseEntity = new ExpenseEntity();

        expenseEntity.setName(expenseDTO.getName());
        expenseEntity.setIconUrl(expenseDTO.getIconUrl());
        expenseEntity.setDate(expenseDTO.getDate());
        expenseEntity.setAmount(expenseDTO.getAmount());
        expenseEntity.setCategoryEntity(categoryEntity);
        expenseEntity.setProfileEntity(profileEntity);

        return expenseEntity;
        
    }
    
    public ExpenseDTO entityToDto(ExpenseEntity expenseEntity) {

        ExpenseDTO expenseDTO = new ExpenseDTO();

        expenseDTO.setId(expenseEntity.getId());
        expenseDTO.setName(expenseEntity.getName());
        expenseDTO.setIconUrl(expenseEntity.getIconUrl());
        expenseDTO.setDate(expenseEntity.getDate());
        expenseDTO.setAmount(expenseEntity.getAmount());
        expenseDTO.setCategoryId(
            expenseEntity.getCategoryEntity() != null ? expenseEntity.getCategoryEntity().getId() : null
        );
        expenseDTO.setCategoryName(
            expenseEntity.getCategoryEntity() != null ? expenseEntity.getCategoryEntity().getName() : "New Expense"
        );
        expenseDTO.setCreationTime(expenseEntity.getCreationTime());
        expenseDTO.setUpdationTime(expenseEntity.getUpdationTime());

        return expenseDTO;

    }

}
