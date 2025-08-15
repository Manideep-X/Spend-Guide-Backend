package com.manideep.spendguide.service;

public interface PushNotifyService {

    // This method will schedule email for everday at 09:30pm IST reminding to add today's income and expense.
    void pushNotifyDaily();
    
    // This method will schedule email for everday at 09:30pm IST reminding to add today's income and expense.
    void notifyExpenseSummaryDaily();

}
