package com.manideep.spendguide.dto;

import java.time.LocalDate;

public class FilterDTO {

    private String type = "expense";
    private LocalDate startDate = LocalDate.MIN;
    private LocalDate endDate = LocalDate.now();
    private String keyword = "";
    private String sortingParameter = "date";
    private String sortingOrder = "desc";
    
    public String getType() {
        return type;
    }
    // Sets type to expense if the field is empty
    public void setType(String type) {
        this.type = type;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    // Sets the start date to the minimum possible date if kept empty
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    // Sets the end date to today is kept empty
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getKeyword() {
        return keyword;
    }
    // Sets the keyword to empty string if the field is empty
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getSortingParameter() {
        return sortingParameter;
    }
    // Sets the sorting parameter to Date if the field is empty
    public void setSortingParameter(String sortingParameter) {
        this.sortingParameter = sortingParameter.trim().isEmpty() ? "date" : sortingParameter;
    }
    
    public String getSortingOrder() {
        return sortingOrder;
    }
    // Sets to ascending order if the field is empty
    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = sortingOrder.trim().isEmpty() ? "asc" : sortingOrder;
    }

}
