package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Account balance(Principal principal) {
        return accountDao.getAccountByUsername(principal.getName());
    }

}
