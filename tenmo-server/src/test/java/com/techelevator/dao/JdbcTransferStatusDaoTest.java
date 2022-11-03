package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferStatusDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferStatusDaoTest extends BaseDaoTests{

    private JdbcTransferStatusDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferStatusDao(jdbcTemplate);
    }

    @Test
    public void getTransferStatusIdByName_returns1_descPending(){

        int result = sut.getTransferStatusIdByName("Pending");

        Assert.assertEquals(1, result);
    }

    @Test
    public void getTransferStatusIdByName_returns2_descApproved(){

        int result = sut.getTransferStatusIdByName("Approved");

        Assert.assertEquals(2, result);
    }

    @Test
    public void getTransferStatusIdByName_returns3_descRejected(){

        int result = sut.getTransferStatusIdByName("Rejected");

        Assert.assertEquals(3, result);
    }

    @Test
    public void getTransferDescriptionById_returnsPending_id1(){

        String result = sut.getTransferDescriptionById(1);

        Assert.assertEquals("Pending", result);
    }

    @Test
    public void getTransferDescriptionById_returnsApproved_id2(){

        String result = sut.getTransferDescriptionById(2);

        Assert.assertEquals("Approved", result);
    }

    @Test
    public void getTransferDescriptionById_returnsRejected_id3(){

        String result = sut.getTransferDescriptionById(3);

        Assert.assertEquals("Rejected", result);
    }
}
