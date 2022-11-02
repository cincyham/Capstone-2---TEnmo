package com.techelevator.tenmo.dao;

public interface TransferStatusDao {
    Integer getTransferStatusIdByName(String name);

    String getTransferDescriptionById(Integer id);
}
