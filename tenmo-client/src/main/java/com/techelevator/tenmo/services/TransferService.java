package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TransferService extends BaseService {

    public TransferService(String baseUrl) {
        super(baseUrl);
    }

    public Boolean approveTransfer(Transfer transfer, AuthenticatedUser user) {
        HttpEntity<Integer> entity = createAuthorizedEntity(transfer.getTransferId(), user);
        ResponseEntity<Boolean> response = restTemplate.exchange( BASE_URL + "api/transfers/id/approve", HttpMethod.PUT, entity, Boolean.class);
        return response.getBody();
    }

    public Boolean rejectTransfer(Transfer transfer, AuthenticatedUser user) {
        HttpEntity<Integer> entity = createAuthorizedEntity(transfer.getTransferId(), user);
        ResponseEntity<Boolean> response = restTemplate.exchange(BASE_URL + "api/transfers/id/reject", HttpMethod.PUT, entity, Boolean.class);
        return response.getBody();
    }

    public Transfer[] getTransfers(AuthenticatedUser user) {
        HttpEntity<Void> entity = createAuthorizedEntity(user);
        ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "api/transfers", HttpMethod.GET, entity, Transfer[].class);
        return response.getBody();
    }

    public Transfer[] getPendingTransfers(AuthenticatedUser user) {
        HttpEntity<Void> entity = createAuthorizedEntity(user);
        ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "api/transfers/pending", HttpMethod.GET, entity, Transfer[].class);
        return response.getBody();
    }

    public Transfer createTransfer(AuthenticatedUser user, Transfer transfer) {
        HttpEntity<Transfer> entity = createAuthorizedEntity(transfer, user);
        ResponseEntity<Transfer> response = restTemplate.exchange(BASE_URL + "api/transfers", HttpMethod.POST, entity, Transfer.class);
        return response.getBody();
    }





}
