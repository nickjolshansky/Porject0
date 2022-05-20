package com.company.dao;

import com.company.entity.Account;
import com.company.entity.User;

import java.sql.ResultSet;
import java.util.List;

public interface UserDao {
    public void insert(User user);
    public void update(User user);
    public void delete(int id);
    public User getUser(ResultSet resultSet);
    public User getUserById(int id);
    public List<User> getAllUsers();
    public boolean checkUsername(String un);
    public User loginUser(String username, String password);

    public void initTables();
}
