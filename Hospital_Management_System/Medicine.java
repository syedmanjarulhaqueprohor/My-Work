package Hospital_Management_System;

class Medicine {
    private String name;
    private double price;
    Medicine(String name,double price){
        this.name=name;
        this.price=price;
    }

    public double getPrice() {
        return price;
    }
}
