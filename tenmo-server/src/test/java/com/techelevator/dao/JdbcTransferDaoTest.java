package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests{

    protected static final Transfer TRANSFER_1 = new Transfer(
            new TransferType(2, "Send"),
            new TransferStatus(1, "Approved"),
            new User("user1"),
            new User("user2"),
            new BigDecimal("99")
    );
    protected static final Transfer TRANSFER_2 = new Transfer(
            new TransferType(2, "Send"),
            new TransferStatus(1, "Approved"),
            new User("user2"),
            new User("user1"),
            new BigDecimal("120")
    );
    protected static final Transfer TRANSFER_3 = new Transfer(
            new TransferType(2, "Request"),
            new TransferStatus(1, "Pending"),
            new User("user1"),
            new User("user3"),
            new BigDecimal("999")
    );
    protected static final Transfer TRANSFER_4 = new Transfer(
            new TransferType(2, "Request"),
            new TransferStatus(1, "Pending"),
            new User("user3"),
            new User("user2"),
            new BigDecimal("200")
    );
    private JdbcTransferDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate, new JdbcUserDao(jdbcTemplate), new JdbcAccountDao(jdbcTemplate), new JdbcTransferStatusDao(jdbcTemplate), new JdbcTransferTypeDao(jdbcTemplate));
    }


    @Test
    public void createTransfer_returnsCorrectTransfer(){
        Transfer transferResult1 = sut.createTransfer(TRANSFER_1);
        Transfer transferResult2 = sut.createTransfer(TRANSFER_2);
        Transfer transferResult3 = sut.createTransfer(TRANSFER_3);

        assertTransferEquals(TRANSFER_1, transferResult1);
        assertTransferEquals(TRANSFER_3, transferResult3);
        assertTransferEquals(TRANSFER_2, transferResult2);
    }

    @Test
    public void listTransfersByUsername_returnsCorrectTransfers_user1(){

        List<Transfer> transfers = sut.listTransfersByUsername("user1");

        Assert.assertEquals(3, transfers.size());

        assertTransferEquals(transfers.get(0), TRANSFER_1);
        assertTransferEquals(transfers.get(1), TRANSFER_2);
        assertTransferEquals(transfers.get(2), TRANSFER_3);
    }

    @Test
    public void listTransfersByUsername_returnsCorrectTransfers_user2(){

        List<Transfer> transfers = sut.listTransfersByUsername("user2");

        Assert.assertEquals(3, transfers.size());

        assertTransferEquals(transfers.get(0), TRANSFER_1);
        assertTransferEquals(transfers.get(1), TRANSFER_2);
        assertTransferEquals(transfers.get(2), TRANSFER_4);
    }

    @Test
    public void listPendingTransfers_returnsCorrectTransfers_user1(){

        List<Transfer> transfers = sut.listPendingTransfersByUsername("user1");

        Assert.assertEquals(1, transfers.size());

        assertTransferEquals(transfers.get(0), TRANSFER_3);
    }

    @Test
    public void listPendingTransfers_returnsCorrectTransfers_user3(){

        List<Transfer> transfers = sut.listPendingTransfersByUsername("user3");

        Assert.assertEquals(2, transfers.size());

        assertTransferEquals(transfers.get(0), TRANSFER_3);
        assertTransferEquals(transfers.get(1), TRANSFER_4);
    }

    @Test
    public void getTransferById_returnsCorrectTransfer_transferId3001(){

        Transfer transfer = sut.getTransferById(3001);

        assertTransferEquals(transfer, TRANSFER_1);
    }

    @Test
    public void getTransferById_returnsCorrectTransfer_transferId3002(){

        Transfer transfer = sut.getTransferById(3002);

        assertTransferEquals(transfer, TRANSFER_2);
    }

    @Test
    public void approveTransfer_returnsTrue_transfer3003(){

        boolean result = sut.approveTransfer(sut.getTransferById(3003));

        Assert.assertTrue(result);

        Assert.assertEquals("Approved", sut.getTransferById(3003).getTransferStatus().getTransferStatusDesc());
    }

    @Test
    public void approveTransfer_returnsTrue_transfer3004(){

        boolean result = sut.approveTransfer(sut.getTransferById(3004));

        Assert.assertTrue(result);

        Assert.assertEquals("Approved", sut.getTransferById(3004).getTransferStatus().getTransferStatusDesc());
    }

    @Test
    public void rejectTransfer_returnsTrue_transfer3003(){

        boolean result = sut.rejectTransfer(sut.getTransferById(3003));

        Assert.assertTrue(result);

        Assert.assertEquals("Rejected", sut.getTransferById(3003).getTransferStatus().getTransferStatusDesc());
    }

    @Test
    public void rejectTransfer_returnsTrue_transfer3004(){

        boolean result = sut.rejectTransfer(sut.getTransferById(3004));

        Assert.assertTrue(result);

        Assert.assertEquals("Rejected", sut.getTransferById(3004).getTransferStatus().getTransferStatusDesc());
    }

    private void assertTransferEquals(Transfer oneTransfer, Transfer twoTransfer){
        Assert.assertEquals(oneTransfer.getTransferType().getTransferTypeDesc(), twoTransfer.getTransferType().getTransferTypeDesc());

        Assert.assertEquals(oneTransfer.getTransferStatus().getTransferStatusDesc(), twoTransfer.getTransferStatus().getTransferStatusDesc());

        Assert.assertEquals(oneTransfer.getUserFrom().getUsername(), twoTransfer.getUserFrom().getUsername());

        Assert.assertEquals(oneTransfer.getUserTo().getUsername(), twoTransfer.getUserTo().getUsername());

        Assert.assertEquals(oneTransfer.getAmount().doubleValue(), twoTransfer.getAmount().doubleValue(), 0.001);
    }
}
