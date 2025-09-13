package com.manideep.spendguide.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.dto.IncomeDTO;
import com.manideep.spendguide.dto.ProfileDTO;
import com.manideep.spendguide.util.EmailTemplateUtil;

@Service
public class ExcelDownEmailServiceImpl implements ExcelDownEmailService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDownEmailServiceImpl.class);

    private final EmailTemplateUtil emailUtil;
    private final EmailService emailService;
    private final ProfileService profileService;
    
    @Value("${app.frontend-url}")
    private String frontendURL;

    public ExcelDownEmailServiceImpl(EmailTemplateUtil emailUtil, EmailService emailService, String frontendURL, ProfileService profileService) {
        this.emailUtil = emailUtil;
        this.emailService = emailService;
        this.frontendURL = frontendURL;
        this.profileService = profileService;
    }

    // Creates an excel file which contains list of all incomes in current month for current user.
    @Override
    public ByteArrayInputStream generateIncomeExcel(List<IncomeDTO> incomes) throws IOException {
        
        // Setting the column headings
        String columnHeadings[] = {"Serial no.", "Name", "Category Name", "Amount", "Recieved Date"};

        // Try-resource block is added so that, if the exception occured the workbook and ByteArrayOutputStream objects will close automatically
        try (Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            
            Sheet sheet = workbook.createSheet("List of Income Details");

            // Creates heading row and give names to it.
            Row headRow = sheet.createRow(0);
            for (int i = 0; i < columnHeadings.length; i++) {
                Cell cell = headRow.createCell(i);
                cell.setCellValue(columnHeadings[i]);
            }

            // Fills the file with data
            int rowNo = 1;
            for (IncomeDTO income : incomes) {
                Row row = sheet.createRow(rowNo);
                row.createCell(0).setCellValue(rowNo++);
                row.createCell(1).setCellValue(income.getName() != null ? income.getName() : "Unavailable");
                row.createCell(1).setCellValue(income.getCategoryName() != null ? income.getCategoryName() : "Unavailable");
                row.createCell(1).setCellValue(income.getAmount() != null ? income.getAmount().doubleValue() : 0.00);
                row.createCell(1).setCellValue(income.getDate() != null ? income.getDate().toString() : "No Date");
            }

            // This will persists the in-memory excel workbook to the output stream
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        }

    }
    
    // Creates an excel file which contains list of all expenses in current month for current user.
    @Override
    public ByteArrayInputStream generateExpenseExcel(List<ExpenseDTO> expenses) throws IOException {
        
        // Setting the column headings
        String columnHeadings[] = {"Serial no.", "Name", "Category Name", "Amount", "Recieved Date"};

        // Try-resource block is added so that, if the exception occured the workbook and ByteArrayOutputStream objects will close automatically
        try (Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            
            Sheet sheet = workbook.createSheet("List of Expense Details");

            // Creates heading row and give names to it.
            Row headRow = sheet.createRow(0);
            for (int i = 0; i < columnHeadings.length; i++) {
                Cell cell = headRow.createCell(i);
                cell.setCellValue(columnHeadings[i]);
            }

            // Fills the file with data
            int rowNo = 1;
            for (ExpenseDTO expense : expenses) {
                Row row = sheet.createRow(rowNo);
                row.createCell(0).setCellValue(rowNo++);
                row.createCell(1).setCellValue(expense.getName() != null ? expense.getName() : "Unavailable");
                row.createCell(1).setCellValue(expense.getCategoryName() != null ? expense.getCategoryName() : "Unavailable");
                row.createCell(1).setCellValue(expense.getAmount() != null ? expense.getAmount().doubleValue() : 0.00);
                row.createCell(1).setCellValue(expense.getDate() != null ? expense.getDate().toString() : "No Date");
            }

            // This will persists the in-memory excel workbook to the output stream
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        }

    }

    // Sends an email which contains list of all incomes in current month for current user.
    @Override
    public void emailMonthlyIncome(List<IncomeDTO> incomes) throws UsernameNotFoundException, IOException {
        
        logger.info("Email Sending... [INCOME LIST]: emailMonthlyIncome()");

        // Fetch currently logged in user details
        try {
            ProfileDTO profileDTO = profileService.getDtoByEmailOrCurrAcc(null);

            // Adds the first name of the user, transaction type and button URL into the email template
            String body = emailUtil.transactionListBody(profileDTO.getFirstName(), "income", frontendURL);

            // Send the email if the body is not null
            if (body != null) {
                emailService.sendEmail(
                    profileDTO.getEmail(),
                    "Spend Guide: Your this month's income list is here",
                    body,
                    generateIncomeExcel(incomes),
                    "List_of_monthly_incomes_of_"+profileDTO.getFirstName()+"_"+profileDTO.getLastName()
                );
                logger.info("Email Sent [INCOME LIST]: emailMonthlyIncome()");
            }
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (RuntimeException | IOException e) {
            throw new IOException(e.getMessage());
        }

    }

    // Sends an email which contains list of all expenses in current month for current user.
    @Override
    public void emailMonthlyExpense(List<ExpenseDTO> expenses) throws UsernameNotFoundException, IOException {
        
        logger.info("Email Sending... [EXPENSE LIST]: emailMonthlyExpense()");

        // Fetch currently logged in user details
        try {
            ProfileDTO profileDTO = profileService.getDtoByEmailOrCurrAcc(null);

            // Adds the first name of the user, transaction type and button URL into the email template
            String body = emailUtil.transactionListBody(profileDTO.getFirstName(), "income", frontendURL);

            // Send the email if the body is not null
            if (body != null) {
                emailService.sendEmail(
                    profileDTO.getEmail(),
                    "Spend Guide: Your this month's expense list is here",
                    body,
                    generateExpenseExcel(expenses),
                    "List_of_monthly_expenses_of_"+profileDTO.getFirstName()+"_"+profileDTO.getLastName()
                );
                logger.info("Email Sent [EXPENSE LIST]: emailMonthlyExpense()");
            }
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (RuntimeException | IOException e) {
            throw new IOException(e.getMessage());
        }

    }

}
