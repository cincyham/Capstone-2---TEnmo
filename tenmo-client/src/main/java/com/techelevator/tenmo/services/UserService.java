package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserService extends BaseService {

    public UserService(String baseUrl) {
        super(baseUrl);
    }

    public boolean userExistsByUsername(AuthenticatedUser user, String username) {
        try {
            HttpEntity<Void> entity = createAuthorizedEntity(user);
            ResponseEntity<Boolean> response = restTemplate.exchange(BASE_URL + "api/users/" + username + "/exists", HttpMethod.GET, entity, Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (Exception ignored){}
        return false;
    }
}
