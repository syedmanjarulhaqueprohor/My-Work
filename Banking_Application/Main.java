package Banking_Application;
class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String sms){
        super(sms);
    }
}
class NegativeAmountException extends Exception{
    public NegativeAmountException(String sms){
        super(sms);
    }
}
public class Main {
    public static void main(String[] args) {
        try {
            Customer customer=new Customer("Prohor");
            Account saving =new SavingsAccount("S001",0.90);
            Account current=new CurrentAccount("C001",0);
            customer.addAccount(saving);
            customer.addAccount(current);
            saving.setBalance(500000);
            saving.calculateInterest();
            saving.deposit(50000);
            saving.transfer(current,5000);
            saving.withdraw(100000);
            System.out.println("Account Details:");
            customer.showAccount();
            System.out.println("Transaction History:");
            saving.TransactionHistory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
