package Banking_Application;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Account>accounts=new ArrayList<>();
    Customer(String name){
        this.name=name;
    }
   public void addAccount(Account account){
        accounts.add(account);
   }
   public void showAccount(){
        for(Account account:accounts){
            System.out.println(account);
        }
   }
}
