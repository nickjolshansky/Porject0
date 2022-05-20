package com.company.entity;

public class Account {
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private int userId;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private float balance;
    public float getBalance() {
        return balance;
    }
    public void setBalance(float balance) {
        if(balance < 0){
            System.out.println("You cannot have a negative balance.");
        }
        else{
            this.balance = balance;
        }
    }

    private boolean approval;
    public boolean isApproval() {
        return approval;
    }
    public void setApproval(boolean approval) {
        this.approval = approval;
    }


    public Account(int id, int userId, String name, float balance, boolean approval) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.approval = approval;
    }
    public Account(int userId, String name, float balance, boolean approval) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.approval = approval;
    }


    @Override
    public String toString() {
        String roundedBalance = String.format("%.2f", this.balance);

        return "{id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", balance=$" + roundedBalance +
                ", approval=" + approval +
                '}';
    }
}