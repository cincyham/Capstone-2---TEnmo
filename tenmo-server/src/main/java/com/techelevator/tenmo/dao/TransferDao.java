package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    /**
     *
     * @param username a tenmo username
     * @return List of transfers
     */

    List<Transfer> listTransfersByUsername(String username);

    /**
     *
     * @param username a tenmo username
     * @return List of pending transfers
     */

    List<Transfer> listPendingTransfersByUsername(String username);

    /**
     *
     * @param transfer a tenmo transfer model object
     * @return the created transfer
     */

    Transfer createTransfer(Transfer transfer);

    /**
     *
     * @param id a tenmo transfer id
     * @return the transfer
     */

    Transfer getTransferById(Integer id);

    /**
     *
     * @param transfer a tenmo transfer model object
     * @return a boolean of the success/failure
     */

    Boolean approveTransfer(Transfer transfer);

    /**
     *
     * @param transfer a tenmo transfer model object
     * @return a boolean of the success/failure
     */

    Boolean rejectTransfer(Transfer transfer);
}
