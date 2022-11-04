package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private int accountId;
    private User user;
    private BigDecimal balance;

    public String getUsername() {
        if (user != null && user.getUsername() != null) {
            return user.getUsername();
        }
        return "";
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account() {

    }

    public Account(String username) {
        this.balance = new BigDecimal("0");
        User user = new User();
        user.setUsername(username);
        setUser(user);
    }


}
