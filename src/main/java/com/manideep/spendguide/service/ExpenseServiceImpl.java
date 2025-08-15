package com.manideep.spendguide.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.entity.CategoryEntity;
import com.manideep.spendguide.entity.ExpenseEntity;
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
    @Override
    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        CategoryEntity categoryEntity = categoryRepository.findById(expenseDTO.getCategoryId()).orElseThrow(
            () -> new RuntimeException("This category is not present!")
        );

        // converting DTO to entity and saving the entity into the database.
        ExpenseEntity expenseEntity = expenseMapper.dtoToEntity(expenseDTO, profileEntity, categoryEntity);
        expenseEntity = expenseRepository.save(expenseEntity);

        return expenseMapper.entityToDto(expenseEntity);

    }

    // Returns all expenses of current month for current user.
    @Override
    public List<ExpenseDTO> getForCurrMonthForCurrAcc() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        
        // Present date and start date of the current month:
        LocalDate presentDate = LocalDate.now();
        LocalDate startDateOfTheMonth = presentDate.withDayOfMonth(1);

        List<ExpenseEntity> expenses = expenseRepository.findByProfileEntity_IdAndDateBetween(profileEntity.getId(), startDateOfTheMonth, presentDate);

        // Converts the list of entity to the list of DTO and returns it.
        // Using method reference: expenses.stream().map(expenseMaper::entityToDto).toList();
        return expenses.stream().map(expense -> expenseMapper.entityToDto(expense)).toList();

    }

    // Delete an expense by it's ID.
    @Override
    public void deleteExpenseById(Long expenseId) {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();

        ExpenseEntity expenseEntity = expenseRepository.findById(expenseId).orElseThrow(
            () -> new RuntimeException("This expense is not present!")
        );

        // Need to check if this expense entity is associated with the currently logged-in user.
        if (!expenseEntity.getProfileEntity().getId().equals(profileEntity.getId())) {
            throw new RuntimeException("This expense is not present!");
        }   
        expenseRepository.delete(expenseEntity);

    }

    // Returns latest 5 expenses of current user.
    @Override
    public List<ExpenseDTO> getLatest5ForCurrAcc() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        
        List<ExpenseEntity> expenses = expenseRepository.findTop5ByProfileEntity_IdOrderByDateDesc(profileEntity.getId());
        return expenses.stream().map(expenseMapper::entityToDto).toList();
        
    }
    
    // Returns the sum of all expense of the current user.
    @Override
    public BigDecimal getTotalForCurrAcc() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        
        BigDecimal totalAmount = expenseRepository.findTotalAmountByProfileEntity_Id(profileEntity.getId());
        // If the value is null then return value will be zero.
        return totalAmount == null ? BigDecimal.ZERO : totalAmount;

    }

    // Return expenses based on start and end date and keyword search
    @Override
    public List<ExpenseDTO> searchByFilter(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {

        ProfileEntity profileEntity = profileService.getCurrentAccount();

        List<ExpenseEntity> filteredExpenses = expenseRepository.findByProfileEntity_IdAndDateBetweenAndNameContainingIgnoreCase(
            profileEntity.getId(), startDate, endDate, keyword, sort
        );

        return filteredExpenses.stream().map(expenseMapper::entityToDto).toList();

    }

    // Returns expenses on a perticular date of current user.(Used for push notification)
    @Override
    public List<ExpenseDTO> getByDateForCurrAcc(Long profileId, LocalDate date) {
        
        List<ExpenseEntity> expenses = expenseRepository.findByProfileEntity_IdAndDate(profileId, date);
        return expenses.stream().map(expenseMapper::entityToDto).toList();

    }

}
