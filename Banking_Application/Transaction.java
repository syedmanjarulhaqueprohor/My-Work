package Banking_Application;

import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private double amount;
    private LocalDateTime dateTime;
    Transaction(String type,double amount){
        this.type=type;
        this.amount=amount;
        this.dateTime=LocalDateTime.now();
    }

    public String toString(){
        return type+"--"+amount+"--"+dateTime;
    }
}
