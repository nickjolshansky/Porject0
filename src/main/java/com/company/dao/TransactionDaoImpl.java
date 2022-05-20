package com.company.dao;

import com.company.entity.Account;
import com.company.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    Connection connection;
    public TransactionDaoImpl(){
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void insert(Transaction transaction) {
        String sql = "insert into transactions (id, user1Id, user2Id, account1Id, account2Id, transactionAmount, request, approval) values (default, ?, ?, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, transaction.getUser1Id());
            preparedStatement.setInt(2, transaction.getUser2Id());
            preparedStatement.setInt(3, transaction.getAccount1Id());
            preparedStatement.setInt(4, transaction.getAccount2Id());
            preparedStatement.setFloat(5, transaction.getTransactionAmount());
            preparedStatement.setBoolean(6,transaction.isRequest());
            preparedStatement.setBoolean(7,transaction.isApproval());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                //System.out.println("Transaction recorded successfully");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int id = resultSet.getInt(1);
                transaction.setId(id);
                //System.out.println("generated id for this transaction is " + id);
            }
            else{
                System.out.println("Something went wrong when recording this transaction");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "update transactions set user1Id = ?, user2Id = ?, account1Id = ?, account2Id = ?, transactionAmount = ?, request = ?, approval = ? where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transaction.getUser1Id());
            preparedStatement.setInt(2, transaction.getUser2Id());
            preparedStatement.setInt(3, transaction.getAccount1Id());
            preparedStatement.setInt(4, transaction.getAccount2Id());
            preparedStatement.setFloat(5, transaction.getTransactionAmount());
            preparedStatement.setBoolean(6, transaction.isRequest());
            preparedStatement.setBoolean(7, transaction.isApproval());
            preparedStatement.setInt(8, transaction.getId());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                //System.out.println("Transaction Update Successful!");
            }
            else{
                System.out.println("something went wrong with the transaction update");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from transactions where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int count = preparedStatement.executeUpdate();

            if(count == 1){
                //System.out.println("Deletion successful");
            }
            else{
                System.out.println("something went wrong");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Transaction getTransaction(ResultSet resultSet){
        try{
            int idData = resultSet.getInt("id");
            int user1IdData = resultSet.getInt("user1Id");
            int user2IdData = resultSet.getInt("user2Id");
            int account1IdData = resultSet.getInt("account1Id");
            int account2IdData = resultSet.getInt("account2Id");
            float transactionAmount = resultSet.getFloat("transactionAmount");
            boolean request = resultSet.getBoolean("request");
            boolean approval = resultSet.getBoolean("approval");

            return new Transaction(idData, user1IdData, user2IdData, account1IdData, account2IdData, transactionAmount, request, approval);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Transaction getTransactionById(int idData) {
        String sql = "select * from transactions where id = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Transaction transaction = getTransaction(resultSet);
                return transaction;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "select * from transactions;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Transaction transaction = getTransaction(resultSet);
                transactions.add(transaction);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> getAllPendingTransactionsByUserId(int userIdData){
        List<Transaction> transactions = new ArrayList<>();

        String sql = "select * from transactions where user2Id = ? and approval = false;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userIdData);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Transaction transaction = getTransaction(resultSet);
                transactions.add(transaction);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public void processTransaction(Transaction transaction){
        AccountDao accountDao = DaoFactory.getAccountDao();
        Account account1 = accountDao.getAccountById(transaction.getAccount1Id());
        Account account2 = accountDao.getAccountById(transaction.getAccount2Id());
        float transactionAmount = transaction.getTransactionAmount();
        float account1Check = account1.getBalance();
        float account2Check = account2.getBalance();

        if(transaction.isRequest()){
            account1.setBalance(account1.getBalance() + transactionAmount);
            account2.setBalance(account2.getBalance() - transactionAmount);
        }
        else{
            account1.setBalance(account1.getBalance() - transactionAmount);
            account2.setBalance(account2.getBalance() + transactionAmount);
        }
        accountDao.update(account1);
        accountDao.update(account2);

        if(account1Check != account1.getBalance() && account2Check != account2.getBalance()){
            transaction.setApproval(true);
            TransactionDao transactionDao = DaoFactory.getTransactionDao();
            transactionDao.update(transaction);

            System.out.println("Transaction complete!");
        }
        else{
            System.out.println("Something went wrong when processing the transaction.");
        }

    }
}
