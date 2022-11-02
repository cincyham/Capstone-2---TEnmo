package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public UserService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean userExistsByUsername(AuthenticatedUser user, String username) {
        HttpEntity<Void> entity = makeAuthEntity(user);
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "api/users/" + username + "/exists", HttpMethod.GET, entity, Boolean.class);
        return response.getBody();
    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }
}
