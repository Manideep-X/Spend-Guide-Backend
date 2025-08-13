package com.manideep.spendguide.service;

import java.math.BigDecimal;
import java.util.List;

import com.manideep.spendguide.dto.IncomeDTO;

public interface IncomeService {

    // save a new Income to the DB for the current user
    public IncomeDTO saveIncome(IncomeDTO incomeDTO);

    // Returns all incomes of current month for current user.
    public List<IncomeDTO> getForCurrMonthForCurrAcc();

    // Delete an income by it's ID.
    public void deleteIncomeById(Long incomeId);

    // Returns latest 5 incomes of current user.
    public List<IncomeDTO> getLatest5ForCurrAcc();

    // Returns the sum of all incomes of current user.
    public BigDecimal getTotalForCurrAcc();

}
