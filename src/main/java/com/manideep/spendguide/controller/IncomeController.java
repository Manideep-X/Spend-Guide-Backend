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
        incomeDTO = incomeService.saveIncome(incomeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(incomeDTO);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getIncomesForCurrMonth() {
        List<IncomeDTO> incomes = incomeService.getForCurrMonthForCurrAcc();
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{income_id}")
    public ResponseEntity<Void> deleteAnIncomeById(@PathVariable("income_id") Long id) {
        incomeService.deleteIncomeById(id);
        return ResponseEntity.noContent().build();
    }

}
