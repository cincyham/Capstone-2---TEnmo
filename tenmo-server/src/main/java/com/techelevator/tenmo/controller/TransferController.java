package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
//@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class TransferController {
    private TransferDao transferDao;
    private TransferStatusDao transferStatusDao;
    private TransferTypeDao transferTypeDao;

    public TransferController(TransferDao transferDao, TransferStatusDao transferStatusDao, TransferTypeDao transferTypeDao) {
        this.transferDao = transferDao;
        this.transferStatusDao = transferStatusDao;
        this.transferTypeDao = transferTypeDao;
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> list(Principal principal) {
        return transferDao.listTransfersByUsername(principal.getName());
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
        return transferDao.createTransfer(transfer);
    }


}
