package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class TransferController {
    private TransferDao transferDao;
    private TransferStatusDao transferStatusDao;
    private TransferTypeDao transferTypeDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, TransferStatusDao transferStatusDao, TransferTypeDao transferTypeDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.transferStatusDao = transferStatusDao;
        this.transferTypeDao = transferTypeDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> list(Principal principal) {
        return transferDao.listTransfersByUsername(principal.getName());
    }

    @RequestMapping(path = "/transfers/id", method = RequestMethod.GET)
    public Transfer getTransferById(@RequestBody Integer transferId) {
        if (transferId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Transfer Id found");
        }
        Transfer transfer = transferDao.getTransferById(transferId);
        if (transfer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Transfer found for id " + transferId);
        }
        return transfer;
    }


    @RequestMapping(path = "/transfers/pending", method = RequestMethod.GET)
    public List<Transfer> listPending(Principal principal) {
        return transferDao.listPendingTransfersByUsername(principal.getName());
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer createTransfer(Principal principal, @RequestBody Transfer transfer) {
        if (transfer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer not provided");
        }

        Transfer createdTransfer = transferDao.createTransfer(transfer);
        if (createdTransfer != null) {
            accountDao.transferMoney(createdTransfer.getAccountFrom(), createdTransfer.getAccountTo(), createdTransfer.getAmount());
            return createdTransfer;
        }
        return null;
    }

    @RequestMapping(path = "/transfers/id/accept", method = RequestMethod.PUT)
    public void acceptTransfer(Principal principal, @RequestBody Integer transferId) {
        Transfer toAccept = transferDao.getTransferById(transferId);
        if (toAccept == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer does not exist");
        } else if (toAccept.getAccountFrom().getBalance().compareTo(toAccept.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
        }

        if (transferDao.approveTransfer(toAccept)) {
            accountDao.transferMoney(toAccept.getAccountFrom(), toAccept.getAccountTo(), toAccept.getAmount());
        }
    }

    @RequestMapping(path = "/transfers/id/reject", method = RequestMethod.PUT)
    public void rejectTransfer(Principal principal, @RequestBody Integer transferId) {
        Transfer toAccept = transferDao.getTransferById(transferId);
        if (toAccept == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer does not exist");
        }
        transferDao.rejectTransfer(toAccept);
    }
}
