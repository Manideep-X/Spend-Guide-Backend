package com.manideep.spendguide.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.entity.CategoryEntity;
import com.manideep.spendguide.entity.IncomeEntity;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.IncomeMapper;
import com.manideep.spendguide.repository.CategoryRepository;
import com.manideep.spendguide.repository.IncomeRepository;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository, IncomeMapper incomeMapper, CategoryRepository categoryRepository, ProfileService profileService) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
        this.profileService = profileService;
        this.categoryRepository = categoryRepository;
    }

    // save a new Income to the DB for the current user
    @Override
    public IncomeDTO saveIncome(IncomeDTO incomeDTO) {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        CategoryEntity categoryEntity = categoryRepository.findById(incomeDTO.getCategoryId()).orElseThrow(
            () -> new RuntimeException("This category is not present!")
        );

        IncomeEntity incomeEntity = incomeMapper.dtoToEntity(incomeDTO, profileEntity, categoryEntity);
        incomeEntity = incomeRepository.save(incomeEntity);

        return incomeMapper.entityToDto(incomeEntity);

    }

    // Returns all incomes of current month for current user.
    @Override
    public List<IncomeDTO> getForCurrMonthForCurrAcc() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        
        // Present date and start date of the current month:
        LocalDate presentDate = LocalDate.now();
        LocalDate startDateOfTheMonth = presentDate.withDayOfMonth(1);

        List<IncomeEntity> incomes = incomeRepository.findByProfileEntity_IdAndDateBetween(profileEntity.getId(), startDateOfTheMonth, presentDate);

        // Converts the list of entity to the list of DTO and returns it.
        // Using method reference: incomes.stream().map(incomeMaper::entityToDto).toList();
        return incomes.stream().map(income -> incomeMapper.entityToDto(income)).toList();

    }

    // Delete an income by it's ID.
    @Override
    public void deleteIncomeById(Long incomeId) {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();

        IncomeEntity incomeEntity = incomeRepository.findById(incomeId).orElseThrow(
            () -> new RuntimeException("This income is not present!")
        );

        // Need to check if this income entity is associated with the currently logged-in user.
        if (!incomeEntity.getProfileEntity().getId().equals(profileEntity.getId())) {
            throw new RuntimeException("This income is not present!");
        }   
        incomeRepository.delete(incomeEntity);

    }

    // Returns latest 5 incomes of current user.
    @Override
    public List<IncomeDTO> getLatest5ForCurrAcc() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();

        List<IncomeEntity> incomes = incomeRepository.findTop5ByProfileEntity_IdOrderByDateDesc(profileEntity.getId());
        return incomes.stream().map(incomeMapper::entityToDto).toList();

    }

    // Returns the sum of all income of the current user.
    @Override
    public BigDecimal getTotalForCurrAcc() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();

        BigDecimal totalAmount = incomeRepository.findTotalAmountByProfileEntity_Id(profileEntity.getId());
        return totalAmount == null ? BigDecimal.ZERO : totalAmount;

    }
    
    

}
