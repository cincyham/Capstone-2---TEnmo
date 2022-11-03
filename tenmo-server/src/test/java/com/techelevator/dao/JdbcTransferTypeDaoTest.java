package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferTypeDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferTypeDaoTest extends BaseDaoTests{

    private JdbcTransferTypeDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferTypeDao(jdbcTemplate);
    }

    @Test
    public void getTransferTypeDescById_returnsRequest_id1(){

        String result = sut.getTransferTypeDescById(1);

        Assert.assertEquals("Request", result);
    }

    @Test
    public void getTransferTypeDescById_returnsSend_id2(){

        String result = sut.getTransferTypeDescById(2);

        Assert.assertEquals("Send", result);
    }

    @Test
    public void getTransferIdByDesc_returns1_descRequest(){

        int result = sut.getTransferIdByDesc("Request");

        Assert.assertEquals(result, 1);
    }

    @Test
    public void getTransferIdByDesc_returns2_descSend(){

        int result = sut.getTransferIdByDesc("Send");

        Assert.assertEquals(result, 2);
    }
}
