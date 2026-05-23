package Banking_Application;

public class SavingsAccount extends Account{
    SavingsAccount(String accountId,double interest){
        super(accountId, interest);
    }

    @Override
    public void setBalance(double balance) {
        super.setBalance(balance);
    }
    @Override
    public void calculateInterest(){
        System.out.println("Total amount:"+getBalance()+getBalance()*interest);
    }
}
