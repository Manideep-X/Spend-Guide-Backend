package com.manideep.spendguide.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.repository.ProfileRepository;
import com.manideep.spendguide.util.EmailTemplateUtil;

@Service
public class PushNotifyServiceImpl implements PushNotifyService {

    // Making it static or else it will create new instance of Logger for every new
    // object of this class.
    private static final Logger logger = LoggerFactory.getLogger(PushNotifyServiceImpl.class);

    private final ProfileRepository profileRepository;
    private final ExpenseService expenseService;
    private final EmailService emailService;
    private final EmailTemplateUtil emailUtil;

    @Value("${app.frontend-url}")
    private String frontendURL;

    public PushNotifyServiceImpl(ProfileRepository profileRepository, ExpenseService expenseService,
            EmailService emailService, EmailTemplateUtil emailUtil) {
        this.profileRepository = profileRepository;
        this.expenseService = expenseService;
        this.emailService = emailService;
        this.emailUtil = emailUtil;
    }

    // This will schedule email for every minute
    // @Scheduled(cron = "0 * * * * *", zone = "IST")

    // This will schedule reminder email for everday at 09:00pm IST
    @Override
    @Scheduled(cron = "0 0 21 * * *", zone = "IST")
    public void pushNotifyDaily() {

        logger.info("Notification Scheduling... [Reminder]: pushNotifyDaily()");
        List<ProfileEntity> profileEntities = profileRepository.findAll();

        // Schedules email for each profile
        for (ProfileEntity profileEntity : profileEntities) {

            // Adds the username and button URL into the email template
            String body = emailUtil.pushNotifyBody(profileEntity.getFirstName(), frontendURL);

            // Schedules the email if the body is not null
            if (body != null) {
                emailService.sendEmail(
                    profileEntity.getEmail(),
                    "Spend Guide Reminder: Add your spending and incomes",
                    body
                );
                logger.info("Notification Scheduled [Reminder]: pushNotifyDaily()");
            }
        }
    }

    // This will schedule today's summary email for everday at 11:00pm IST
    @Override
    @Scheduled(cron = "0 0 23 * * *", zone = "IST")
    @Transactional(readOnly = true)
    public void notifyExpenseSummaryDaily() {

        logger.info("Notification Scheduling... [Expense Summary]: notifyExpenseSummaryDaily()");
        List<ProfileEntity> profileEntities = profileRepository.findAll();

        for (ProfileEntity profileEntity : profileEntities) {

            List<ExpenseDTO> expensesToday = expenseService.getByDateForCurrAcc(profileEntity.getId(),LocalDate.now());

            // Creates the table by put all info regarding expenses for each user
            if (!expensesToday.isEmpty()) {
                StringBuilder tbody = new StringBuilder();
                int i = 1;
                for (ExpenseDTO expenseDTO : expensesToday) {
                    tbody
                    .append(i % 2 == 1 ? emailUtil.getTrStart1st() : emailUtil.getTrStart2nd())
                    .append(emailUtil.getTdStart()).append(i++).append(emailUtil.getTdEnd())
                    .append(emailUtil.getTdStart()).append(expenseDTO.getName()).append(emailUtil.getTdEnd())
                    .append(emailUtil.getTdStart())
                    .append(expenseDTO.getCategoryId() == null ? "N/A" : expenseDTO.getCategoryName())
                    .append(emailUtil.getTdEnd())
                    .append(emailUtil.getTdStartAmount()).append(expenseDTO.getAmount()).append(emailUtil.getTdEnd())
                    .append(emailUtil.getTrEnd());
                }

                // Adds the table and username and button URL into the email template
                String body = emailUtil.expenseSummaryBody(profileEntity.getFirstName(), tbody.toString(), frontendURL);

                // Schedules the email if the body is not null
                if (body != null) {
                    emailService.sendEmail(
                        profileEntity.getEmail(), 
                        "Spend Guide Summary: Your daily spending summary",
                        body);
                    logger.info("Notification Scheduled [Expense Summary]: notifyExpenseSummaryDaily()");
                }
            }
        }
    }

}
