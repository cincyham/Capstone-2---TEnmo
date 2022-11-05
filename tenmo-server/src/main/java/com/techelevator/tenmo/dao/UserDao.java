package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int id);

    /**
     *
     * @param username tenmo username
     * @return tenmo user model object
     */

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    User getUserByAccountId(Integer accountId);

    /**
     *
     * @param username tenmo username
     * @return boolean of success/failure
     */

    boolean userExistsByUsername(String username);
}
