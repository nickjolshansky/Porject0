package com.company.dao;

public class DaoFactory {
    private static AccountDao accountDao;
    private static UserDao userDao;
    private static TransactionDao transactionDao;

    private DaoFactory(){}

    public static AccountDao getAccountDao(){
        if(accountDao == null){
            accountDao = new AccountDaoImpl();
        }

        return accountDao;
    }

    public static UserDao getUserDao(){
        if(userDao == null){
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    public static TransactionDao getTransactionDao(){
        if(transactionDao == null){
            transactionDao = new TransactionDaoImpl();
        }

        return transactionDao;
    }
}