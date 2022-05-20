package com.company.dao;

import com.company.entity.Account;
import com.company.entity.Transaction;

import java.sql.ResultSet;
import java.util.List;

public interface TransactionDao {
    public void insert(Transaction transaction);
    public void update(Transaction transaction);
    public void delete(int idData);
    public Transaction getTransaction(ResultSet resultSet);
    public Transaction getTransactionById(int idData);
    public List<Transaction> getAllTransactions();
    public List<Transaction> getAllPendingTransactionsByUserId(int userIdData);
    public void processTransaction(Transaction transaction);
}