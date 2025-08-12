package com.manideep.spendguide.service;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.ExpenseMapper;
import com.manideep.spendguide.repository.CategoryRepository;
import com.manideep.spendguide.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, ExpenseMapper expenseMapper, ProfileService profileService) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.expenseMapper = expenseMapper;
        this.profileService = profileService;
    }
    
    // save a new expense to the DB for the current user
    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        

    }

}
