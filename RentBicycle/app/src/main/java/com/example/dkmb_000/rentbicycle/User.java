package com.example.dkmb_000.rentbicycle;

/**
 * Contains information about User
 */

public class User {

    private String userId;
    private String username;
    private String email;
    private String password;
    private int accountBalance = 20; //saldo konta

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountBalance() {
        return accountBalance;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }


}
