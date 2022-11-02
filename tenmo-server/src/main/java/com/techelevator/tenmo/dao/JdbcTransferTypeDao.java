package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getTransferTypeDescById(Integer id) {
        String sql =
                "SELECT transfer_type_desc " +
                "FROM transfer_type " +
                "WHERE transfer_type_id = ?;";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    @Override
    public Integer getTransferIdByDesc(String desc) {
        String sql =
                "SELECT transfer_type_id " +
                "FROM transfer_type " +
                "WHERE transfer_type_desc = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, desc);
    }
}
