package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountByUsername(String username) {
        String sql =
                "SELECT account.account_id, account.user_id, account.balance " +
                "FROM account " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE tenmo_user.username = ? " +
                "LIMIT 1;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            return accountMapper(rowSet);
        }
        return null;
    }

    @Override
    public Account getAccountById(Integer id) {
        String sql =
            "SELECT account.account_id, account.user_id, account.balance " +
            "FROM account " +
            "WHERE account.account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            return accountMapper(rowSet);
        }
        return null;
    }

    @Override
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);
        BigDecimal newToBalance = toAccount.getBalance().add(amount);
        String sql =
                "UPDATE account " +
                "SET balance = ? " +
                "WHERE account_id = ?;" +
                "UPDATE account " +
                "SET balance = ? " +
                "WHERE account_id = ?;";

        jdbcTemplate.update(sql, newFromBalance, fromAccount.getAccountId(), newToBalance, toAccount.getAccountId());
    }

    private Account accountMapper(SqlRowSet rowSet) {
        try {
            Account account = new Account();
            account.setAccountId(rowSet.getInt("account_id"));
            account.setUserId(rowSet.getInt("user_id"));
            account.setBalance(rowSet.getBigDecimal("balance"));
            return account;
        } catch (Exception ignored) {
            //TODO: impliment
        }
        return null;
    }
}
