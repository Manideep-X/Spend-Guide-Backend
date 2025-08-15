package com.manideep.spendguide.mapper;

import org.springframework.stereotype.Component;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.dto.LatestTransactionDTO;

// Injecting this class will help to map from IncomeDTO or ExpenseDTO to LatestTransactionDTO with profile ID.
// Each method returns an object.
@Component
public class LatestTransactionMapper {

    private final String incomeType = "income"; 
    private final String expenseType = "expense"; 

    public LatestTransactionDTO incomeToTransactionDto(IncomeDTO incomeDTO, Long profileId) {

        LatestTransactionDTO latestTransactionDTO = new LatestTransactionDTO();

        latestTransactionDTO.setId(incomeDTO.getId());
        latestTransactionDTO.setProfileId(profileId);
        latestTransactionDTO.setIconUrl(incomeDTO.getIconUrl());
        latestTransactionDTO.setName(incomeDTO.getName());
        latestTransactionDTO.setAmount(incomeDTO.getAmount());
        latestTransactionDTO.setDate(incomeDTO.getDate());
        latestTransactionDTO.setType(incomeType);
        latestTransactionDTO.setCreationTime(incomeDTO.getCreationTime());
        latestTransactionDTO.setUpdationTime(incomeDTO.getUpdationTime());

        return latestTransactionDTO;

    }

    public LatestTransactionDTO expenseToTransactionDto(ExpenseDTO expenseDTO, Long profileId) {

        LatestTransactionDTO latestTransactionDTO = new LatestTransactionDTO();

        latestTransactionDTO.setId(expenseDTO.getId());
        latestTransactionDTO.setProfileId(profileId);
        latestTransactionDTO.setIconUrl(expenseDTO.getIconUrl());
        latestTransactionDTO.setName(expenseDTO.getName());
        latestTransactionDTO.setAmount(expenseDTO.getAmount());
        latestTransactionDTO.setDate(expenseDTO.getDate());
        latestTransactionDTO.setType(expenseType);
        latestTransactionDTO.setCreationTime(expenseDTO.getCreationTime());
        latestTransactionDTO.setUpdationTime(expenseDTO.getUpdationTime());

        return latestTransactionDTO;

    }

}
