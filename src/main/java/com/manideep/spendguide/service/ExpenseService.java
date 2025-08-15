package com.manideep.spendguide.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.manideep.spendguide.dto.ExpenseDTO;

public interface ExpenseService {

    // Saves and returns a new expense to the DB for the current user.
    ExpenseDTO saveExpense(ExpenseDTO expenseDTO);

    // Returns all expenses of current month for current user.
    List<ExpenseDTO> getForCurrMonthForCurrAcc();

    // Delete an expense by it's ID.
    void deleteExpenseById(Long expenseId);

    // Returns latest 5 expenses of current user.
    List<ExpenseDTO> getLatest5ForCurrAcc();

    // Returns the sum of all expense of the current user.
    BigDecimal getTotalForCurrAcc();

    // Return expenses based on start and end date and keyword search
    List<ExpenseDTO> searchByFilter(LocalDate startDate, LocalDate endDate, String keyword, Sort sort);

    // Returns expenses on a perticular date of current user.
    List<ExpenseDTO> getByDateForCurrAcc(Long profileId, LocalDate date);

}
