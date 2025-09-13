package com.manideep.spendguide.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.service.ExcelDownEmailService;
import com.manideep.spendguide.service.ExpenseService;
import com.manideep.spendguide.service.IncomeService;


@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelDownEmailService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public ExcelController(ExcelDownEmailService excelService, IncomeService incomeService,
            ExpenseService expenseService) {
        this.excelService = excelService;
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    // Sends income list as an excel file to frontend for download
    @GetMapping("/download-income")
    public ResponseEntity<InputStreamResource> downloadIncomes() {
        
        try {
            // Get the lists of income in current month for current user
            List<IncomeDTO> incomes = incomeService.getForCurrMonthForCurrAcc();

            // Get the excel file containing all the income details for this month
            ByteArrayInputStream excelFileOfIncome = excelService.generateIncomeExcel(incomes);

            // Adding the header data
            HttpHeaders headerPart = new HttpHeaders();
            headerPart.add("Content-Disposition", "attachment; filename=list_of_incomes.xlsx");
            
            return ResponseEntity.ok()
                .headers(headerPart)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelFileOfIncome));

        } catch (UsernameNotFoundException e) {
            // Response code: 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            // Response code: 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    // Sends expense list as an excel file to frontend for download
    @GetMapping("/download-expense")
    public ResponseEntity<InputStreamResource> downloadExpenses() {
        
        try {
            // Get the lists of income in current month for current user
            List<ExpenseDTO> expenses = expenseService.getForCurrMonthForCurrAcc();

            // Get the excel file containing all the income details for this month
            ByteArrayInputStream excelFileOfExpense = excelService.generateExpenseExcel(expenses);

            // Adding the header data
            HttpHeaders headerPart = new HttpHeaders();
            headerPart.add("Content-Disposition", "attachment; filename=list_of_incomes.xlsx");
            
            return ResponseEntity.ok()
                .headers(headerPart)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml-sheet"))
                .body(new InputStreamResource(excelFileOfExpense));

        } catch (UsernameNotFoundException e) {
            // Response code: 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            // Response code: 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    // Sends an email with income list as an excel file attachment to the email address of the current user
    @GetMapping("/email-income")
    public ResponseEntity<Void> sendIncomeListEmail() {
        
        try {
            // Get the lists of income in current month for current user
            List<IncomeDTO> incomes = incomeService.getForCurrMonthForCurrAcc();

            // Send an email to the current user which includes income list as excel attachment
            excelService.emailMonthlyIncome(incomes);

            // Response code: 204 but no content
            return ResponseEntity.noContent().build();

        } catch (UsernameNotFoundException e) {
            // Response code: 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            // Response code: 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    // Sends an email with expense list as an excel file attachment to the email address of the current user
    @GetMapping("/email-expense")
    public ResponseEntity<Void> sendExpenseListEmail() {
        
        try {
            // Get the lists of income in current month for current user
            List<ExpenseDTO> expenses = expenseService.getForCurrMonthForCurrAcc();

            // Send an email to the current user which includes income list as excel attachment
            excelService.emailMonthlyExpense(expenses);

            // Response code: 204 but no content
            return ResponseEntity.noContent().build();

        } catch (UsernameNotFoundException e) {
            // Response code: 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            // Response code: 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
