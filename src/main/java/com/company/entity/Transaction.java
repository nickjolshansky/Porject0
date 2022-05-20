package com.company.entity;

public class Transaction {
    private int id;
    private int user1Id;
    private int user2Id;
    private int account1Id;
    private int account2Id;
    private float transactionAmount;
    private boolean request;
    private boolean approval;

    public Transaction(int id, int user1Id, int user2Id, int account1Id, int account2Id, float transactionAmount, boolean request, boolean approval) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.account1Id = account1Id;
        this.account2Id = account2Id;
        this.transactionAmount = transactionAmount;
        this.request = request;
        this.approval = approval;
    }

    public Transaction(int user1Id, int user2Id, int account1Id, int account2Id, float transactionAmount, boolean request, boolean approval) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.account1Id = account1Id;
        this.account2Id = account2Id;
        this.transactionAmount = transactionAmount;
        this.request = request;
        this.approval = approval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public int getAccount1Id() {
        return account1Id;
    }

    public void setAccount1Id(int account1Id) {
        this.account1Id = account1Id;
    }

    public int getAccount2Id() {
        return account2Id;
    }

    public void setAccount2Id(int account2Id) {
        this.account2Id = account2Id;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    @Override
    public String toString() {
        String roundedBalance = String.format("%.2f", this.transactionAmount);

        return "Transaction{" +
                "id=" + id +
                ", user1Id=" + user1Id +
                ", user2Id=" + user2Id +
                ", account1Id=" + account1Id +
                ", account2Id=" + account2Id +
                ", transactionAmount=" + transactionAmount +
                ", request=" + request +
                ", approval=" + approval +
                '}';
    }
}
