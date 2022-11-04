package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private AccountDao accountDao;
    private TransferStatusDao transferStatusDao;
    private TransferTypeDao transferTypeDao;



    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao, AccountDao accountDao, TransferStatusDao transferStatusDao, TransferTypeDao transferTypeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferStatusDao = transferStatusDao;
        this.transferTypeDao = transferTypeDao;
    }

    @Override
    public List<Transfer> listTransfersByUsername(String username) {
        Integer id = accountDao.getAccountByUsername(username).getAccountId();
        String sql =
                "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE account_from = ? " +
                "OR account_to = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id, id);
        return transferListMapper(rowSet);
    }

    @Override
    public List<Transfer> listPendingTransfersByUsername(String username) {
        Integer userId = accountDao.getAccountByUsername(username).getAccountId();
        Integer pendingId = transferStatusDao.getTransferStatusIdByName("Pending");
        String sql =
                "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE (account_from = ? " +
                "OR account_to = ?) " +
                "AND transfer_status_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId, userId, pendingId);
        return transferListMapper(rowSet);
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        Integer transferTypeId = transferTypeDao.getTransferTypeIdByDesc(transfer.getTransferType().getTransferTypeDesc());
        Integer transferStatusId = transferStatusDao.getTransferStatusIdByName(transfer.getTransferStatus().getTransferStatusDesc());
        Account fromAccount = accountDao.getAccountByUsername(transfer.getAccountFrom().getUsername());
        Account toAccount =  accountDao.getAccountByUsername(transfer.getAccountTo().getUsername());
        String sql =
                "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING transfer_id;";
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId, fromAccount.getAccountId(), toAccount.getAccountId(), transfer.getAmount());
        return getTransferById(transferId);
    }

    @Override
    public Transfer getTransferById(Integer id) {
        String sql =
                "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            return transferMapper(rowSet);
        }
        return null;
    }

    @Override
    public Boolean approveTransfer(Transfer transfer) {
        try {
            Integer approvedStatusId = transferStatusDao.getTransferStatusIdByName("Approved");
            String sql =
                    "UPDATE transfer " +
                            "SET transfer_status_id = ? " +
                            "WHERE transfer_id = ?;";
            jdbcTemplate.update(sql, approvedStatusId, transfer.getTransferId());
            return true;
        } catch (Exception ignored) {}
        return false;
    }

    @Override
    public Boolean rejectTransfer(Transfer transfer) {
        try {
            Integer rejectedStatusId = transferStatusDao.getTransferStatusIdByName("Rejected");
            String sql =
                    "UPDATE transfer " +
                            "SET transfer_status_id = ? " +
                            "WHERE transfer_id = ?;";
            jdbcTemplate.update(sql, rejectedStatusId, transfer.getTransferId());
            return true;
        } catch (Exception ignored) {}
        return false;
    }

    private List<Transfer> transferListMapper(SqlRowSet rowSet) {
        try {
            List<Transfer> transfers = new ArrayList<>();
            while (rowSet.next()) {
                transfers.add(transferMapper(rowSet));
            }
            return transfers;
        } catch (Exception ignored) {
            //TODO: implement
        }
        return null;
    }

    private Transfer transferMapper(SqlRowSet rowSet) {
        try {
            Transfer transfer = new Transfer();
            transfer.setTransferId(rowSet.getInt("transfer_id"));
            transfer.setTransferType(
                    new TransferType(
                            rowSet.getInt("transfer_type_id"),
                            transferTypeDao.getTransferTypeDescById(rowSet.getInt("transfer_type_id"))
                    )
            );
            transfer.setTransferStatus(
                    new TransferStatus(
                            rowSet.getInt("transfer_status_id"),
                            transferStatusDao.getTransferDescriptionById(rowSet.getInt("transfer_status_id"))
                    )
            );
            transfer.setAccountFrom(accountDao.getAccountById(rowSet.getInt("account_from")));
            transfer.setAccountTo(accountDao.getAccountById(rowSet.getInt("account_to")));
            transfer.setAmount(rowSet.getBigDecimal("amount"));
            return transfer;
        } catch (Exception ignored) {
            //TODO: implement
        }
        return null;
    }

}
