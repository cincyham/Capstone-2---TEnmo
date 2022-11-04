package com.techelevator.controller;

import com.techelevator.controller.model.AuthenticatedUser;
import com.techelevator.controller.model.UserCredentials;
import com.techelevator.dao.TestingDatabaseConfig;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestingDatabaseConfig.class)
public class BaseControllerTests {

    @Autowired
    protected DataSource dataSource;

    protected String token;
    protected RestTemplate restTemplate = new RestTemplate();

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    protected void setToken() {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(new UserCredentials("a", "a"));
        try {
            ResponseEntity<AuthenticatedUser> response =
            restTemplate.exchange("http://localhost:8080/login", HttpMethod.POST, entity, AuthenticatedUser.class);
            token = response.getBody().getToken();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private HttpEntity<UserCredentials> createCredentialsEntity(UserCredentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(credentials, headers);
    }

    protected <T> HttpEntity<T> getAuthorizedEntity(Class<T> type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<T> entity = new HttpEntity<>(headers);
        return entity;
    }
}
