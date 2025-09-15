package com.manideep.spendguide.dto;

import java.time.LocalDate;

public class FilterDTO {

    private String type = "expense";
    private LocalDate startDate = LocalDate.of(2000, 1, 1);
    private LocalDate endDate = LocalDate.now();
    private String keyword = "";
    private String sortingParameter = "date";
    private String sortingOrder = "desc";
    
    public String getType() {
        return type;
    }
    // Sets type to expense if the field is empty
    public void setType(String type) {
        this.type = (type == null || type.trim().isEmpty()) ? "expense" : type;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    // Sets the start date to the minimum possible date if kept empty
    public void setStartDate(LocalDate startDate) {
        // Posgress is showing error for this LocalDate.MIN as "data out of range"😅
        // this.startDate = (startDate == null) ? LocalDate.MIN : startDate;
        this.startDate = (startDate == null) ? LocalDate.of(2000, 1, 1) : startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    // Sets the end date to today is kept empty
    public void setEndDate(LocalDate endDate) {
        this.endDate = (endDate == null) ? LocalDate.now() : endDate;
    }
    
    public String getKeyword() {
        return keyword;
    }
    // Sets the keyword to empty string if the field is empty
    public void setKeyword(String keyword) {
        this.keyword = (keyword == null || keyword.trim().isEmpty()) ? "" : keyword;
    }
    
    public String getSortingParameter() {
        return sortingParameter;
    }
    // Sets the sorting parameter to Date if the field is empty
    public void setSortingParameter(String sortingParameter) {
        this.sortingParameter = (sortingParameter == null || sortingParameter.trim().isEmpty()) ? "date" : sortingParameter;
    }
    
    public String getSortingOrder() {
        return sortingOrder;
    }
    // Sets to ascending order if the field is empty
    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = (sortingOrder == null || sortingOrder.trim().isEmpty()) ? "asc" : sortingOrder;
    }

}
