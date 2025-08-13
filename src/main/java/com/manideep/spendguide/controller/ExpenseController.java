package com.manideep.spendguide.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        expenseDTO = expenseService.saveExpense(expenseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpensesForCurrMonth() {
        List<ExpenseDTO> expenses = expenseService.getForCurrMonthForCurrAcc();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{expense_id}")
    public ResponseEntity<Void> deleteAnExpenseById(@PathVariable("expense_id") Long id) {
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }

}
