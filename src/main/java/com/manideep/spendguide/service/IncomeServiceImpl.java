package com.manideep.spendguide.service;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.mapper.IncomeMapper;
import com.manideep.spendguide.repository.IncomeRepository;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryService categoryService;
    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeRepository incomeRepository, CategoryService categoryService, IncomeMapper incomeMapper) {
        this.incomeRepository = incomeRepository;
        this.categoryService = categoryService;
        this.incomeMapper = incomeMapper;
    }
    
    

}
