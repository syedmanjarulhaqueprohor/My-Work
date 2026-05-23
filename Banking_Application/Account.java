package Banking_Application;

import java.util.ArrayList;
import java.util.List;

public class Account {
    protected String accountId;
    protected double balance;
    protected double interest;
    private List<Transaction> transactions=new ArrayList<>();
    Account(String accountId,double interest){
        this.accountId=accountId;
        this.interest=interest;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws NegativeAmountException{
       if(amount<0){
           throw new NegativeAmountException("Amount must be positive");
       }
       balance+=amount;
       transactions.add(new Transaction("Deposit",amount));
    }
    public void withdraw(double amount) throws InsufficientFundsException {
        if(balance<amount){
            throw new InsufficientFundsException("Not enough balance");
        }
        balance-=amount;
        transactions.add(new Transaction("Withdraw",amount));
    }
    public void transfer(Account account,double amount) throws NegativeAmountException,InsufficientFundsException{
        this.withdraw(amount);
        account.deposit(amount);
        transactions.add(new Transaction("Transfer to "+account.accountId,amount));
    }
    public void TransactionHistory(){
        for (Transaction transaction:transactions){
            System.out.println(transaction);
        }
    }
    public void calculateInterest(){
    }
}
