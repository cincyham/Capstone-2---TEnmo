package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> listTransfersByUsername(String username);

    List<Transfer> listPendingTransfersByUsername(String username);

    Transfer createTransfer(Transfer transfer);

    Transfer getTransferById(Integer id);


}
