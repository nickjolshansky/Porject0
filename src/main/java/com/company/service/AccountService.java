package com.company.service;

import com.company.dao.AccountDao;
import com.company.dao.DaoFactory;
import com.company.dao.TransactionDao;
import com.company.entity.Account;
import com.company.entity.Transaction;
import org.company.App;

import java.util.List;
import java.util.Scanner;

public class AccountService {
    public static void insertAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name you want associated with this account: ");
        String name = scanner.nextLine();
        System.out.println("Please enter the starting balance for this account: ");
        float balance = scanner.nextFloat();

        Account account = new Account(App.currentUser.getId(), name, balance, false);
        AccountDao accountDao = DaoFactory.getAccountDao();
        accountDao.insert(account);
        System.out.println("You have now applied the account for approval!");
    }
    public static void updateAccount(){
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("What is the id of the account you would like to update?");
        int idData = scannerInt.nextInt();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the new name you want associated with this account: ");
        String name = scanner.nextLine();
        System.out.println("Please enter the new balance for this account: ");
        float balance = scanner.nextFloat();
        System.out.println("Is this account approved? 1 for yes 0 for no.");
        int accountApprovalChoice = scanner.nextInt();
        boolean accountApproved = false;
        if(accountApprovalChoice == 1){
            accountApproved = true;
        }

        Account account = new Account(idData, App.currentUser.getId(), name, balance, accountApproved);
        AccountDao accountDao = DaoFactory.getAccountDao();

        accountDao.update(account);
    }
    public static void deleteAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter id of account to be deleted");
        int id = scanner.nextInt();

        AccountDao accountDao = DaoFactory.getAccountDao();

        System.out.println(accountDao.getAccountById(id) + " has been deleted.");

        accountDao.delete(id);
    }

    public static void withdrawFromAccount(){
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("Please enter the id of the account you would like to withdraw from?");
        int idData = scannerInt.nextInt();
        while(getAccountById(idData).getUserId() != App.currentUser.getId()){
            System.out.println("Please enter a valid option:");
            idData = scannerInt.nextInt();
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the amount you'd like to withdraw from this account: ");
        float withdrawAmount = scanner.nextFloat();
        while(withdrawAmount < 0){
            System.out.println("Please enter a valid option:");
            withdrawAmount = scanner.nextFloat();
        }
        Account account = getAccountById(idData);
        float balanceCheck = account.getBalance();
        account.setBalance(account.getBalance() - withdrawAmount);

        AccountDao accountDao = DaoFactory.getAccountDao();
        accountDao.update(account);

        if(balanceCheck != account.getBalance()){
            Transaction transaction = new Transaction(App.currentUser.getId(), 0, account.getId(), 0, withdrawAmount, true, true);
            TransactionDao transactionDao = DaoFactory.getTransactionDao();
            transactionDao.insert(transaction);
        }
    }
    public static void depositToAccount(){
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("Please enter the id of the account you would like to deposit into?");
        int idData = scannerInt.nextInt();
        while(getAccountById(idData).getUserId() != App.currentUser.getId()){
            System.out.println("Please enter a valid option:");
            idData = scannerInt.nextInt();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the amount you'd like to deposit into this account: ");
        float depositAmount = scanner.nextFloat();
        while(depositAmount < 0){
            System.out.println("Please enter a valid option:");
            depositAmount = scanner.nextFloat();
        }

        Account account = getAccountById(idData);
        float balanceCheck = account.getBalance();
        account.setBalance(account.getBalance() + depositAmount);

        if(balanceCheck != account.getBalance()) {
            Transaction transaction = new Transaction(App.currentUser.getId(), 0, account.getId(), 0, depositAmount, false, true);
            TransactionDao transactionDao = DaoFactory.getTransactionDao();
            transactionDao.insert(transaction);
        }

        AccountDao accountDao = DaoFactory.getAccountDao();
        accountDao.update(account);
    }

    public static Account getAccountById(int idData){
        AccountDao accountDao = DaoFactory.getAccountDao();
        Account account = accountDao.getAccountById(idData);
        return account;
    }

    public static void printAccountById(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the id of the account: ");
        int idData = scanner.nextInt();

        AccountDao accountDao = DaoFactory.getAccountDao();
        Account account = accountDao.getAccountById(idData);

        System.out.println("here is the account you requested: " + account.toString());
    }

    public static void getAllAccounts(){
        System.out.println("All accounts: ");
        AccountDao accountDao = DaoFactory.getAccountDao();
        List<Account> accounts = accountDao.getAllAccounts();

        for(Account a : accounts ){
            System.out.println(a);
        }
    }

    public static void getAllUserAccounts(int idData){
        Scanner scanner = new Scanner(System.in);
        //
        System.out.println("All accounts: ");
        AccountDao accountDao = DaoFactory.getAccountDao();
        List<Account> accounts = accountDao.getAllUserAccounts(idData);

        for(Account a : accounts ){
            System.out.println(a);
        }
    }

    public static void approveAccountById(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter id of account to be approved: ");
        int idData = scanner.nextInt();

        AccountDao accountDao = DaoFactory.getAccountDao();
        Account account = accountDao.getAccountById(idData);

        System.out.println(account.toString());
        System.out.println("Type 1 to approve account or 0 to reject account:");
        int choice = scanner.nextInt();
        if(choice == 1){
            account.setApproval(true);
            accountDao.update(account);
        }
        else{
            accountDao.delete(account.getId());
        }


    }
}