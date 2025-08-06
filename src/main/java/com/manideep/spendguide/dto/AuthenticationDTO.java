package com.manideep.spendguide.dto;

public class AuthenticationDTO {

    private String email;
    private String password;
    private String jwtToken;
    
    public AuthenticationDTO() {
    }
    public AuthenticationDTO(String email, String password, String jwtToken) {
        this.email = email;
        this.password = password;
        this.jwtToken = jwtToken;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwtToken() {
        return jwtToken;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
