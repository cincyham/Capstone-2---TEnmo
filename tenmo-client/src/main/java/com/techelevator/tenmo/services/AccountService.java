package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService extends BaseService{

    public AccountService(String baseUrl) {
        super(baseUrl);
    }

    public Account getBalance(AuthenticatedUser user) {
        HttpEntity<Void> entity = createAuthorizedEntity(user);
        ResponseEntity<Account> response = restTemplate.exchange(BASE_URL + "api/account", HttpMethod.GET, entity, Account.class);
        return response.getBody();
    }
}
