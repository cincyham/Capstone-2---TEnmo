package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.text.Bidi;

public class JdbcAccountDaoTest extends BaseDaoTests{

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountByUsername_returnsCorrectAccount_user1(){
        Account result = sut.getAccountByUsername("user1");

        assertAccountsEqual(result, 2001, 1001, 1000.0);
    }

    @Test
    public void getAccountByUsername_returnsCorrectAccount_user2(){
        Account result = sut.getAccountByUsername("user2");

        assertAccountsEqual(result, 2002, 1002, 1000.0);
    }

    @Test
    public void getAccountById_returnsCorrectAccount_accountId2001(){

        Account result = sut.getAccountById(2001);

        assertAccountsEqual(result, 2001, 1001, 1000);
    }

    @Test
    public void getAccountById_returnsCorrectAccount_accountId2002(){

        Account result = sut.getAccountById(2002);

        assertAccountsEqual(result, 2002, 1002, 1000);
    }

    @Test
    public void transferMoney_removedMoneyFromAccount2001_addedMoneyToAccount2002(){

        sut.transferMoney(sut.getAccountById(2001), sut.getAccountById(2002), new BigDecimal("9.50"));

        Assert.assertEquals(990.5, sut.getAccountById(2001).getBalance().doubleValue(), 0.001);

        Assert.assertEquals(1009.50, sut.getAccountById(2002).getBalance().doubleValue(), 0.001);
    }

    @Test
    public void transferMoney_removedMoneyFromAccount2002_addedMoneyToAccount2003(){

        sut.transferMoney(sut.getAccountById(2002), sut.getAccountById(2003), new BigDecimal("9.50"));

        Assert.assertEquals(990.5, sut.getAccountById(2002).getBalance().doubleValue(), 0.001);

        Assert.assertEquals(1009.50, sut.getAccountById(2003).getBalance().doubleValue(), 0.001);
    }


    private void assertAccountsEqual(Account account, int account_id, int user_id, double balance){
        Assert.assertEquals(account.getAccountId(), account_id);
        Assert.assertEquals(user_id, account.getUserId());
        Assert.assertEquals(balance, account.getBalance().doubleValue(), 0.0001);
    }
}
