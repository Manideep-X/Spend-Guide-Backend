package com.manideep.spendguide.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface DashboardService {

    // Returns all details related to incomes and expenses that will get displayed in the Dashboard.
    Map<String, Object> getDashboardDetails() throws UsernameNotFoundException;

}
