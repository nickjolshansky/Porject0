package com.company.dao;

import com.company.entity.Account;
import org.company.App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{

    Connection connection;
    public AccountDaoImpl(){
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void insert(Account account) {
        String sql = "insert into accounts (id, userId, name, balance, approval) values (default, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, App.currentUser.getId());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setFloat(3, account.getBalance());
            preparedStatement.setBoolean(4, account.isApproval());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                //System.out.println("Account added successfully");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int id = resultSet.getInt(1);
                account.setId(id);
                //System.out.println("generated id for this account is " + id);
            }
            else{
                System.out.println("Something went wrong adding the account");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        String sql = "update accounts set name = ?, balance = ?, approval = ? where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setFloat(2, account.getBalance());
            preparedStatement.setBoolean(3, account.isApproval());
            preparedStatement.setInt(4, account.getId());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                //System.out.println("Update Successful!");
            }
            else{
                System.out.println("something went wrong with the update");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from accounts where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int count = preparedStatement.executeUpdate();

            if(count == 1){
                System.out.println("Deletion successful");
            }
            else{
                System.out.println("something went wrong");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccount(ResultSet resultSet){
        try{
            int idData = resultSet.getInt("id");
            int userIdData = resultSet.getInt("userId");
            String name = resultSet.getString("name");
            float balance = resultSet.getFloat("balance");
            boolean approval = resultSet.getBoolean("approval");

            return new Account(idData, userIdData, name, balance, approval);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Account getAccountById(int idData) {
        String sql = "select * from accounts where id = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getAccount(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Account> getAllUserAccounts(int idData) {
        List<Account> accounts = new ArrayList<>();

        String sql = "select * from accounts where userId = ? and approval = true;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Account account = getAccount(resultSet);
                accounts.add(account);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        String sql = "select * from accounts;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Account account = getAccount(resultSet);
                accounts.add(account);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return accounts;
    }
}
