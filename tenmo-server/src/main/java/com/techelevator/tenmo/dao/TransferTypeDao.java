package com.techelevator.tenmo.dao;

import org.springframework.stereotype.Component;

@Component
public interface TransferTypeDao {
    String getTransferTypeDescById(Integer id);

    Integer getTransferIdByDesc(String desc);
}
