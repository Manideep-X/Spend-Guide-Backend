package com.manideep.spendguide.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manideep.spendguide.dto.IncomeDTO;

public interface IncomeService {

    // save a new Income to the DB for the current user
    IncomeDTO saveIncome(IncomeDTO incomeDTO) throws UsernameNotFoundException, RuntimeException;

    // Returns all incomes of current month for current user.
    List<IncomeDTO> getForCurrMonthForCurrAcc() throws UsernameNotFoundException;

    // Delete an income by it's ID.
    void deleteIncomeById(Long incomeId) throws UsernameNotFoundException, RuntimeException;

    // Returns latest 5 incomes of current user.
    List<IncomeDTO> getLatest5ForCurrAcc() throws UsernameNotFoundException;

    // Returns the sum of all incomes of current user.
    BigDecimal getTotalForCurrAcc() throws UsernameNotFoundException;

    // Return incomes based on start and end date and keyword search
    List<IncomeDTO> searchByFilter(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) throws UsernameNotFoundException;

    // Returns incomes on a perticular date of current user.
    // List<IncomeDTO> getByDateForCurrAcc(Long profileId, LocalDate date);

}
