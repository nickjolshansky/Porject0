package com.company.service;

import com.company.dao.DaoFactory;
import com.company.dao.TransactionDao;
import com.company.entity.Transaction;
import org.company.App;

import java.util.List;
import java.util.Scanner;

public class TransactionService {

    public static void requestTransaction(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the User id that you would like to transact with: ");
        int user2Id = scanner.nextInt();

        System.out.println("Are you requesting money or sending money?");
        System.out.println("Please enter 1 for request or 0 for send.");
        int transferStyle = scanner.nextInt();
        while (transferStyle != 1 && transferStyle != 0){
            System.out.println("Please enter a valid option.");
            System.out.println("Are you requesting money or sending money?");
            System.out.println("Please enter 1 for request or 0 for send.");
            transferStyle = scanner.nextInt();
        }
        boolean isRequest;
        if(transferStyle == 1){
            isRequest = true;
        }else{
            isRequest = false;
        }

        System.out.println("Please enter the account id you would like to associate with this transaction: ");
        int account1Id = scanner.nextInt();
        System.out.println("Please enter the amount you would like to transfer: ");
        float transactionAmount = scanner.nextFloat();

        Transaction transaction = new Transaction(App.currentUser.getId(), user2Id, account1Id, 0, transactionAmount, isRequest, false);
        TransactionDao transactionDao = DaoFactory.getTransactionDao();
        transactionDao.insert(transaction);
        System.out.println("You have successfully initialized a transaction: " + transaction.toString());
    }

    public static void viewPendingTransactions(){
        TransactionDao transactionDao = DaoFactory.getTransactionDao();
        List<Transaction> pendingTransactions = transactionDao.getAllPendingTransactionsByUserId(App.currentUser.getId());
        if(pendingTransactions.size() == 0){
            System.out.println("No pending transactions.");
            return;}
        System.out.println("All pending transactions: ");
        for(Transaction t : pendingTransactions ){
            System.out.println(t);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the id associated with the transaction you'd like to use or type 0 to return to the main menu.");
        int choice = scanner.nextInt();
        if(choice == 0){
            return;
        }
        else{
            Transaction chosenTransaction = transactionDao.getTransactionById(choice);

            System.out.println("Please type 1 to approve or 0 to decline this transaction.");
            int approvalChoice = scanner.nextInt();
            while(approvalChoice != 1 && approvalChoice != 0){
                System.out.println("please choose a valid input.");
                approvalChoice = scanner.nextInt();
            }
            if(approvalChoice == 1){
                System.out.println("Please type the id of the account you would like to associate with this transaction");
                int accountChoice = scanner.nextInt();
                chosenTransaction.setAccount2Id(accountChoice);
                transactionDao.processTransaction(chosenTransaction);
            }
            else if(approvalChoice == 0){
                transactionDao.delete(choice);
            }
        }
    }

    public static void printTransactionById(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type the id of the transaction you'd like to lookup:");
        int idData = scanner.nextInt();
        TransactionDao transactionDao = DaoFactory.getTransactionDao();
        Transaction transaction = transactionDao.getTransactionById(idData);
        System.out.println(transaction.toString());
    }

    public static Transaction getTransactionById(int idData){
        TransactionDao transactionDao = DaoFactory.getTransactionDao();
        Transaction transaction = transactionDao.getTransactionById(idData);
        return transaction;
    }
    public static void printAllTransactions(){
        TransactionDao transactionDao = DaoFactory.getTransactionDao();
        List<Transaction> transactions = transactionDao.getAllTransactions();
        System.out.println("All transactions: ");
        for(Transaction t : transactions ){
            System.out.println(t);
        }
    }
}