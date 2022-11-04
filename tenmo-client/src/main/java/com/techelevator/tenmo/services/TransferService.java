package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Boolean approveTransfer(Transfer transfer, AuthenticatedUser user) {
        HttpHeaders headers = getAuthorizedHeaders(user);
        HttpEntity<Integer> entity = new HttpEntity<>(transfer.getTransferId(), headers);
        ResponseEntity<Boolean> response = restTemplate.exchange( baseUrl + "api/transfers/id/approve", HttpMethod.PUT, entity, Boolean.class);
        return response.getBody();
    }

    public Boolean rejectTransfer(Transfer transfer, AuthenticatedUser user) {
        HttpHeaders headers = getAuthorizedHeaders(user);
        HttpEntity<Integer> entity = new HttpEntity<>(transfer.getTransferId(), headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "api/transfers/id/reject", HttpMethod.PUT, entity, Boolean.class);
        return response.getBody();
    }

    public Transfer[] getTransfers(AuthenticatedUser user) {
        HttpEntity<Void> entity = makeAuthEntity(user);
        ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "api/transfers", HttpMethod.GET, entity, Transfer[].class);
        return response.getBody();
    }

    public Transfer[] getPendingTransfers(AuthenticatedUser user) {
        HttpEntity<Void> entity = makeAuthEntity(user);
        ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "api/transfers/pending", HttpMethod.GET, entity, Transfer[].class);
        return response.getBody();
    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser user) {
        HttpHeaders headers = getAuthorizedHeaders(user);
        return new HttpEntity<>(headers);
    }

    public Transfer createTransfer(AuthenticatedUser user, Transfer transfer) {
        HttpHeaders headers = getAuthorizedHeaders(user);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "api/transfers", HttpMethod.POST, entity, Transfer.class);
        return response.getBody();
    }

    private HttpHeaders getAuthorizedHeaders(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        return headers;
    }

}
