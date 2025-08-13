package com.manideep.spendguide.service;

import java.math.BigDecimal;
import java.util.List;

import com.manideep.spendguide.dto.ExpenseDTO;

public interface ExpenseService {

    // save a new expense to the DB for the current user.
    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO);

    // Returns all expenses of current month for current user.
    public List<ExpenseDTO> getForCurrMonthForCurrAcc();

    // Delete an expense by it's ID.
    public void deleteExpenseById(Long expenseId);

    // Returns latest 5 expenses of current user.
    public List<ExpenseDTO> getLatest5ForCurrAcc();

    // Returns the sum of all expense of the current user.
    public BigDecimal getTotalForCurrAcc();

}
