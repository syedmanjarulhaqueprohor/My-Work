package com.example.M_11;

import java.time.LocalDate;

public class Model {
    private Long id;
    private String title;
    private double amount;
    private LocalDate date;
    Model(){
    }
    public Model(Long id,String title,double amount,LocalDate date){
        this.id=id;
        this.title=title;
        this.amount=amount;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
