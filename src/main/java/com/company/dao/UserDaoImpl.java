package com.company.dao;

import com.company.entity.Account;
import com.company.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    Connection connection;
    public UserDaoImpl(){
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void insert(User user) {
        String sql = "call insert_user(?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isEmployee());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                System.out.println("User created successfully.");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int id = resultSet.getInt(1);
                user.setId(id);
                System.out.println("generated id for this user is " + id);
            }
            else{
                //System.out.println("Something went wrong adding the account");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "select * from users;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = getUser(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean checkUsername(String username){
        boolean foundMatch = false;
        String sql = "SELECT * FROM users WHERE username like ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                foundMatch = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return foundMatch;
    }

    @Override
    public User loginUser(String username, String password){
        //username
        boolean usernameMatch = false;
        String sql = "SELECT * FROM users WHERE username like '" + username + "'";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                usernameMatch = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        //password
        boolean passwordMatch = false;
        String sql2 = "SELECT * FROM users WHERE pass like '" + password + "'";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                passwordMatch = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        //check validity
        if(usernameMatch && passwordMatch){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    User user = getUser(resultSet);
                    return user;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User getUser(ResultSet resultSet){
        try{
            int idData = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("pass");
            boolean isEmployee = resultSet.getBoolean("isEmployee");

            return new User(idData, username, password, isEmployee);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void initTables(){
        String sql = "Create Table user(id SERIAL PRIMARY KEY, name varchar(50), password varchar(50), isEmployee boolean);";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
