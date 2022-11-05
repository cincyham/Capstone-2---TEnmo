package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class BaseService {

    protected final String BASE_URL;

    protected final RestTemplate restTemplate = new RestTemplate();

    public BaseService(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    /**
     * Create an authorized entity with a body of class type
     * @param body the object to be in the body of the entity
     * @param user the authenticated user
     * @return HttpEntity of type T
     * @param <T> the class of the object in the body
     */
    protected <T> HttpEntity<T> createAuthorizedEntity(T body, AuthenticatedUser user) {
        return new HttpEntity<>(body, getAuthorizedHeaders(user));
    }

    /**
     * Used for entities with an empty body
     * @param user the authenticated user
     * @return HttpEntity of Void
     */
    protected HttpEntity<Void> createAuthorizedEntity(AuthenticatedUser user) {
        return new HttpEntity<>(getAuthorizedHeaders(user));
    }

    protected HttpHeaders getAuthorizedHeaders(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        return headers;
    }
}
