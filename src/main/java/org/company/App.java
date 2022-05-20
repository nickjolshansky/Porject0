package org.company;

import com.company.entity.User;
import com.company.service.AccountService;
import com.company.service.TransactionService;
import com.company.service.UserService;

import java.util.Scanner;

public class App {
    public static boolean loggedIn;
    public static User currentUser;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean breakFlag = true;
        while(breakFlag){
            if (!loggedIn) {
                System.out.println("Please select an option:");
                System.out.println("1. Create new user");
                System.out.println("2. Login");
                System.out.println("3. Quit application");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        UserService.createNewUser();
                        break;

                    case 2:
                        UserService.loginUser();
                        break;

                    case 3:
                        breakFlag = false;
                        break;

                    default:
                        System.out.println("Please enter a valid option.");
                }
            }
            else{
                if(currentUser.isEmployee()){
                    System.out.println("Please select an option:");
                    System.out.println("1. Lookup an account by id");
                    System.out.println("2. Lookup all accounts");
                    System.out.println("3. Lookup a transaction by id");
                    System.out.println("4. Lookup all transactions");
                    System.out.println("5. Approve pending accounts");
                    System.out.println("6. Delete an account by id");
                    System.out.println("7. Logout");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            AccountService.printAccountById();
                            break;

                        case 2:
                            AccountService.getAllAccounts();
                            break;

                        case 3:
                            TransactionService.printTransactionById();
                            break;

                        case 4:
                            TransactionService.printAllTransactions();
                            break;

                        case 5:
                            AccountService.approveAccountById();
                            break;

                        case 6:
                            AccountService.deleteAccount();
                            break;

                        case 7:
                            loggedIn = false;
                            currentUser = null;
                            break;

                        default:
                            System.out.println("please enter a valid option.");
                            break;
                    }
                }
                else{
                    System.out.println("Please select an option:");
                    System.out.println("1. Apply for a new account.");
                    System.out.println("2. Lookup all accounts.");
                    System.out.println("3. Make a withdrawal.");
                    System.out.println("4. Make a deposit.");
                    System.out.println("5. Check pending transaction.");
                    System.out.println("6. Request a new transaction.");
                    System.out.println("7. Logout");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            AccountService.insertAccount();
                            break;

                        case 2:
                            AccountService.getAllUserAccounts(App.currentUser.getId());
                            break;

                        case 3:
                            AccountService.withdrawFromAccount();
                            break;

                        case 4:
                            AccountService.depositToAccount();
                            break;

                        case 5:
                            TransactionService.viewPendingTransactions();
                            break;

                        case 6:
                            TransactionService.requestTransaction();
                            break;

                        case 7:
                            loggedIn = false;
                            currentUser = null;
                            break;

                        default:
                            System.out.println("please enter a valid option.");
                            break;
                    }
                }
            }
            System.out.println();
        }
    }
}


/*
make and call a stored procedure

test all flow
 */