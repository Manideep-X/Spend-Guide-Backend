package com.manideep.spendguide.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailTemplateUtil.class);

    private final String trStart1st = "<tr style='background-color: #f9fafb;'>";
    private final String trStart2nd = "<tr style='background-color: #ffffff;'>";
    private final String trEnd = "</tr>";
    private final String tdStart = "<td style='color: #374151; padding: 12px; border-bottom: 1px solid #e5e7eb;'>";
    private final String tdStartAmount = "<td style='color: #dc2626; padding: 12px; text-align: right; border-bottom: 1px solid #e5e7eb; font-weight: 600;'>";
    private final String tdEnd = "</td>";
    
    // Getter methods for all final fields
    public String getTrStart1st() {
        return trStart1st;
    }
    
    public String getTrStart2nd() {
        return trStart2nd;
    }
    
    public String getTrEnd() {
        return trEnd;
    }
    
    public String getTdStart() {
        return tdStart;
    }
    
    public String getTdStartAmount() {
        return tdStartAmount;
    }
    
    public String getTdEnd() {
        return tdEnd;
    }

    // Fetch email template from recources
    private String loadEmailTemplate(String templateName) throws IOException {

        ClassPathResource resource = new ClassPathResource("email/" + templateName);

        // Try to convert the template to string
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Fail to fetch Email template: loadEmailTemplate() : {}", templateName);
            throw new IOException("Failed to load email template ", e);
        }

    }

    // Fetches notification template and replace the parameters inside it.
    public String pushNotifyBody(String firstName, String buttonUrl) {

        // Gets the email template
        String emailTemplate = null;
        try {
            emailTemplate = loadEmailTemplate("email-reminder.html");
        } catch (IOException e) {
            logger.error("Fail to send daily email: pushNotifyDaily(): ", e);
            return null;
        }

        // Replaces the parameters
        String pushNotifyBody = null;
        if (emailTemplate != null) {
            pushNotifyBody = emailTemplate
                            .replace("{{FIRST_NAME}}", firstName)
                            .replace("{{BUTTON_URL}}", buttonUrl);
        }
        return pushNotifyBody;
    }

    // Fetches summary template and replace the parameters inside it.
    public String expenseSummaryBody(String firstName, String tbody, String buttonUrl) {

        // Gets the email template
        String emailTemplate = null;
        try {
            emailTemplate = loadEmailTemplate("email-expense-summary.html");
        } catch (IOException e) {
            logger.error("Fail to send daily email: pushNotifyDaily(): ", e);
            return null;
        }

        // Replaces the parameters
        String expenseSummaryBody = null;
        if (emailTemplate != null) {
            expenseSummaryBody = emailTemplate
                            .replace("{{FIRST_NAME}}", firstName)
                            .replace("{{TBODY}}", tbody)
                            .replace("{{BUTTON_URL}}", buttonUrl);
        }
        return expenseSummaryBody;
    }

}
