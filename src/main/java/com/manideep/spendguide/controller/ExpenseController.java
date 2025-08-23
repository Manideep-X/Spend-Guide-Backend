package com.manideep.spendguide.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.service.ExpenseService;


@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> saveNewExpense(@RequestBody ExpenseDTO expenseDTO) {

        // This will send FORBIDDEN status if the current user not logged-in or token expired
        // And will send NOT_FOUND if the category is not present for current expense
        try {
            expenseDTO = expenseService.saveExpense(expenseDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(expenseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpensesForCurrMonth() {
        
        // This will send FORBIDDEN status if the current user not logged-in or token expired
        List<ExpenseDTO> expenses = null;
        try {
            expenses = expenseService.getForCurrMonthForCurrAcc();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{expense_id}")
    public ResponseEntity<Void> deleteAnExpenseById(@PathVariable("expense_id") Long id) {
        
        // This will send FORBIDDEN status if the current user not logged-in or token expired
        // And will send NOT_FOUND if the category is not present for current expense
        try {
            expenseService.deleteExpenseById(id);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        return ResponseEntity.noContent().build();
    }

}
