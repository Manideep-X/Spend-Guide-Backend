package com.manideep.spendguide.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.ExpenseDTO;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.repository.ProfileRepository;

@Service
public class PushNotifyServiceImpl implements PushNotifyService {

    // Making it static or else it will create new instance of Logger for every new
    // object of this class.
    private static final Logger logger = LoggerFactory.getLogger(PushNotifyServiceImpl.class);

    private final ProfileRepository profileRepository;
    private final ExpenseService expenseService;
    private final EmailService emailService;

    private final String trStart = "<tr style='background-color: #f9fafb;'>";
    private final String trEnd = "</tr>";
    private final String tdStart = "<td style='color: #374151; padding: 12px; border-bottom: 1px solid #e5e7eb;'>";
    private final String tdStartAmount = "<td style='color: #dc2626; padding: 12px; text-align: right; border-bottom: 1px solid #e5e7eb; font-weight: 600;'>";
    private final String tdEnd = "</td>";

    @Value("${app.frontend-url}")
    private String frontendURL;

    public PushNotifyServiceImpl(ProfileRepository profileRepository, ExpenseService expenseService,
            EmailService emailService) {
        this.profileRepository = profileRepository;
        this.expenseService = expenseService;
        this.emailService = emailService;
    }

    // Fetch email push notification template from recources
    private String loadEmailTemplate(String templateName) throws IOException {

        ClassPathResource resource = new ClassPathResource("email/" + templateName);

        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Fail to fetch Email template: loadEmailTemplate() : {}", templateName);
            throw new IOException("Failed to load email template ", e);
        }

    }

    // This will schedule email for every minute
    // @Scheduled(cron = "0 * * * * *", zone = "IST")

    // This will schedule reminder email for everday at 09:00pm IST
    @Override
    @Scheduled(cron = "0 0 21 * * *", zone = "IST")
    public void pushNotifyDaily() {

        logger.info("Notification Scheduled [Reminder]: pushNotifyDaily()");
        List<ProfileEntity> profileEntities = profileRepository.findAll();

        String emailTemplate = null;
        try {
            emailTemplate = loadEmailTemplate("email-reminder.html");
        } catch (IOException e) {
            logger.error("Fail to send daily email: pushNotifyDaily(): ", e);
        }

        if (emailTemplate != null) {
            for (ProfileEntity profileEntity : profileEntities) {

                String body = emailTemplate
                        .replace("{{FIRST_NAME}}", profileEntity.getFirstName())
                        .replace("{{BUTTON_URL}}", frontendURL);

                emailService.sendEmail(profileEntity.getEmail(), "Spend Guide Reminder: Add your spending and incomes",
                        body);
            }
            logger.info("Notification Sending Completed [Reminder]: pushNotifyDaily()");
        }
    }

    // This will schedule today's summary email for everday at 11:00pm IST
    @Scheduled(cron = "0 0 23 * * *", zone = "IST")
    @Override
    public void notifyExpenseSummaryDaily() {

        logger.info("Notification Scheduled [Expense Summary]: notifyExpenseSummaryDaily()");
        List<ProfileEntity> profileEntities = profileRepository.findAll();

        String emailTemplate = null;
        try {
            emailTemplate = loadEmailTemplate("email-reminder.html");
        } catch (IOException e) {
            logger.error("Fail to send daily email: pushNotifyDaily(): ", e);
        }

        for (ProfileEntity profileEntity : profileEntities) {

            List<ExpenseDTO> expensesToday = expenseService.getByDateForCurrAcc(profileEntity.getId(),
                    LocalDate.now());

            if (!expensesToday.isEmpty() && emailTemplate != null) {
                StringBuilder tbody = new StringBuilder();
                int i = 1;
                for (ExpenseDTO expenseDTO : expensesToday) {
                    tbody.append(trStart);
                    tbody.append(tdStart).append(i++).append(tdEnd);
                    tbody.append(trEnd);
                }
            }
        }

        logger.info("Notification Sending Completed [Expense Summary]: notifyExpenseSummaryDaily()");

    }

}
