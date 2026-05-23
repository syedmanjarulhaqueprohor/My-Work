package Banking_Application;

public class CurrentAccount extends Account{
    private double transactionFee=10;
    CurrentAccount(String accountId,double interest){
        super(accountId, interest);
    }
    @Override
    public void setBalance(double balance) {
        super.setBalance(balance);
    }
    @Override
    public void calculateInterest(){
        System.out.println("Total amount:"+balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException{
        super.withdraw(amount+transactionFee);
    }
}
