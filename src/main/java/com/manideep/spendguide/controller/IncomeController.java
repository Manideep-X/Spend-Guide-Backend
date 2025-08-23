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

import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }
    
    @PostMapping
    public ResponseEntity<IncomeDTO> saveNewIncome(@RequestBody IncomeDTO incomeDTO) {

        // This will send FORBIDDEN status if the current user not logged-in or token expired
        // And will send NOT_FOUND if the category is not present for current income
        try {
            incomeDTO = incomeService.saveIncome(incomeDTO);
        } catch (UsernameNotFoundException ue) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(incomeDTO);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getIncomesForCurrMonth() {
        
        // This will send FORBIDDEN status if the current user not logged-in or token expired
        List<IncomeDTO> incomes = null;
        try {
            incomes = incomeService.getForCurrMonthForCurrAcc();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{income_id}")
    public ResponseEntity<Void> deleteAnIncomeById(@PathVariable("income_id") Long id) {
        
        // This will send FORBIDDEN status if the current user not logged-in or token expired
        // And will send NOT_FOUND if the category is not present for current income
        try {
            incomeService.deleteIncomeById(id);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.noContent().build();
    }

}
