package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    Account getAccountByUsername(String username);

    Account getAccountById(Integer id);

    void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount);
}
