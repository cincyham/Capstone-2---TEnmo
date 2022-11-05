package com.techelevator.tenmo.dao;

public interface TransferStatusDao {

    /**
     *
     * @param name transfer status name
     * @return transfer status id
     */
    Integer getTransferStatusIdByName(String name);

    /**
     *
     * @param id transfer status id
     * @return transfer status description
     */

    String getTransferDescriptionById(Integer id);
}
