package com.company.dao;

import com.company.entity.Account;

import java.sql.ResultSet;
import java.util.List;

public interface AccountDao {
    public void insert(Account account);
    public void update(Account account);
    public void delete(int id);
    public Account getAccount(ResultSet resultSet);
    public Account getAccountById(int id);
    public List<Account> getAllUserAccounts(int id);
    public List<Account> getAllAccounts();
}
