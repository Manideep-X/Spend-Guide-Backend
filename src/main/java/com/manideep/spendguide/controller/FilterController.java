package com.manideep.spendguide.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.dto.FilterDTO;
import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.service.ExpenseService;
import com.manideep.spendguide.service.IncomeService;

@RestController
@RequestMapping("/filter")
public class FilterController {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public FilterController(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<?> filterLatestTransactions(@RequestBody FilterDTO filterDTO) {

        Sort sortingOrderAndBy = "desc".equalsIgnoreCase(filterDTO.getSortingOrder())
                ? Sort.by(Sort.Direction.DESC, filterDTO.getSortingParameter())
                : Sort.by(Sort.Direction.ASC, filterDTO.getSortingParameter());

        if ("expense".equalsIgnoreCase(filterDTO.getType())) {
            try {
                List<ExpenseDTO> expenses = expenseService.searchByFilter(
                        filterDTO.getStartDate(), filterDTO.getEndDate(), filterDTO.getKeyword(), sortingOrderAndBy);
                // Returns status 200 with list of expenses if user searched for expense.
                return ResponseEntity.ok(expenses);
            } catch (UsernameNotFoundException e) {

                // Returns status FORBIDDEN if token is expired or invalid for current user.
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "message", "expense"
                ));
            }
        }

        try {
            List<IncomeDTO> incomes = incomeService.searchByFilter(
                    filterDTO.getStartDate(), filterDTO.getEndDate(), filterDTO.getKeyword(), sortingOrderAndBy);

            // Returns status 200 with list of incomes if user didn't search for expense.
            return ResponseEntity.ok(incomes);
        } catch (UsernameNotFoundException e) {

            // Returns status FORBIDDEN if token is expired or invalid for current user.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                "message", "income"
            ));
        }
    }

}
