package com.company.service;

import com.company.dao.AccountDao;
import com.company.dao.DaoFactory;
import com.company.dao.UserDao;
import com.company.dao.UserDaoImpl;
import com.company.entity.User;
import org.company.App;

import java.util.Scanner;

public class UserService {
    public static void createNewUser(){
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = DaoFactory.getUserDao();

        System.out.println("Please enter the username you'd like:");
        String newUsername = scanner.nextLine();

        //this code checks for a username match. I'm ignoring it for now because it doesn't work
        while(userDao.checkUsername(newUsername)){
            System.out.println("That username is taken.");
            System.out.println("Please enter the username you'd like:");
            newUsername = scanner.nextLine();
        }

        System.out.println("Please enter the password you'd like:");
        String newPassword = scanner.nextLine();

        boolean isNewEmployee = false;
        boolean breakFlag = true;
        while(breakFlag){
            System.out.println("Is this user an employee?");
            System.out.println("Enter 1 for yes or 0 for no.");
            int employeeChoice = scanner.nextInt();
            switch (employeeChoice){
                case 0:
                    isNewEmployee = false;
                    breakFlag = false;
                    break;
                case 1:
                    isNewEmployee = true;
                    breakFlag = false;
                    break;
                default:
                    System.out.println("Please select a valid option.");
            }
        }

        User user = new User(newUsername, newPassword, isNewEmployee);
        userDao.insert(user);
        System.out.println("You have now added a new user: " + user.toString());
    }

    public static void loginUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your username:");
        String loginUsername = scanner.nextLine();
        System.out.println("Please enter your password:");
        String loginPassword = scanner.nextLine();

        UserDao userDao = DaoFactory.getUserDao();
        User loginUser = userDao.loginUser(loginUsername, loginPassword);

        if(loginUser != null){
            App.currentUser = loginUser;
            App.loggedIn = true;
        }
        else{
            System.out.println("The username or password you entered does not match our records.");
        }

    }
}
