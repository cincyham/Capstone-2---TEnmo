package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer getTransferStatusIdByName(String name) {
        String sql =
                "SELECT transfer_status_id " +
                "FROM transfer_status " +
                "WHERE transfer_status_desc = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }

    @Override
    public String getTransferDescriptionById(Integer id) {
        String sql =
                "SELECT transfer_status_desc " +
                "FROM transfer_status " +
                "WHERE transfer_status_id = ?;";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

}
