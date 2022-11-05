package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    /**
     *
     * @param username tenmo username
     * @return tenmo account model object
     */

    Account getAccountByUsername(String username);

    /**
     *
     * @param id tenmo account id
     * @return tenmo account model object
     */

    Account getAccountById(Integer id);

    /**
     *
     * @param fromAccount tenmo account model object
     * @param toAccount tenmo account model object
     * @param amount tenmo account balance
     */

    void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount);
}
