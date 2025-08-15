package com.manideep.spendguide.service;

import java.util.Map;

public interface DashboardService {

    // Returns all details related to incomes and expenses that will get displayed in the Dashboard.
    Map<String, Object> getDashboardDetails();

}
