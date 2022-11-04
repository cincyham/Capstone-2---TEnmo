package com.techelevator.controller;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountControllerTests extends BaseControllerTests{

    private AccountController sut;


    @Before
    public void setup() {
        setToken();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        AccountDao accountDao = new JdbcAccountDao(jdbcTemplate, new JdbcUserDao(jdbcTemplate));
        sut = new AccountController(accountDao);
    }

    @Test
    public void getAccount_returnsCorrectAccount_ForAA() {

    }
}
