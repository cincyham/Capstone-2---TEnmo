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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }

    public Transfer createTransfer(AuthenticatedUser user, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "api/transfers", HttpMethod.POST, entity, Transfer.class);
        return response.getBody();
    }

}
