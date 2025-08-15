package com.manideep.spendguide.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.dto.LatestTransactionDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.LatestTransactionMapper;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProfileService profileService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final LatestTransactionMapper latestTransactionMapper;
    
    public DashboardServiceImpl(ProfileService profileService, IncomeService incomeService,
            ExpenseService expenseService, LatestTransactionMapper latestTransactionMapper) {
        this.profileService = profileService;
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.latestTransactionMapper = latestTransactionMapper;
    }

    // Returns all details related to incomes and expenses that will get displayed in the Dashboard.
    @Override
    public Map<String, Object> getDashboardDetails() {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        List<IncomeDTO> last5Incomes = incomeService.getLatest5ForCurrAcc();
        List<ExpenseDTO> last5Expenses = expenseService.getLatest5ForCurrAcc();

        // Concatinate both the list of income and expense after mapping each one to the transaction DTO and then sorting based on date (decreasing order)
        List<LatestTransactionDTO> last10Transactions = Stream.concat(
            last5Incomes.stream()
            .map(income -> latestTransactionMapper.incomeToTransactionDto(income, profileEntity.getId())),
            last5Expenses.stream()
            .map(expense -> latestTransactionMapper.expenseToTransactionDto(expense, profileEntity.getId()))
        )
        .sorted(Comparator
            .comparing((LatestTransactionDTO transaction) -> transaction.getDate(), Comparator.reverseOrder())
            .thenComparing(transaction -> transaction.getCreationTime(), Comparator.reverseOrder())
        )
        .toList();

        // returns every required data in a Map.
        return Map.of(
            "totalBalance", incomeService.getTotalForCurrAcc().subtract(expenseService.getTotalForCurrAcc()),
            "totalIncome", incomeService.getTotalForCurrAcc(),
            "totalExpense", expenseService.getTotalForCurrAcc(),
            "last5Incomes", last5Incomes,
            "last5Expenses", last5Expenses,
            "last10Transactions", last10Transactions
        );

    }  

}
