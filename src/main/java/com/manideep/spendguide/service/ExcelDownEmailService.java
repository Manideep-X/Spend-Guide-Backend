package com.manideep.spendguide.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.dto.IncomeDTO;

public interface ExcelDownEmailService {

    // Creates an excel file which contains list of all incomes in current month for current user.
    ByteArrayInputStream generateIncomeExcel(List<IncomeDTO> incomes) throws IOException;
    
    // Creates an excel file which contains list of all expenses in current month for current user.
    ByteArrayInputStream generateExpenseExcel(List<ExpenseDTO> expenses) throws IOException;
    
    // Sends an email which contains list of all incomes in current month for current user.
    void emailMonthlyIncome(List<IncomeDTO> incomes) throws UsernameNotFoundException, IOException;
    
    // Sends an email which contains list of all expenses in current month for current user.
    void emailMonthlyExpense(List<ExpenseDTO> expenses) throws UsernameNotFoundException, IOException;

}
