package com.techelevator.tenmo.dao;

import org.springframework.stereotype.Component;

@Component
public interface TransferTypeDao {

    /**
     *
     * @param id transfer type id
     * @return transfer type description
     */

    String getTransferTypeDescById(Integer id);

    /**
     *
     * @param desc transfer type description
     * @return transfer type id
     */

    Integer getTransferTypeIdByDesc(String desc);
}
