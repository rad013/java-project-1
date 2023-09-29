package main;

public class Customer {
    private int custPatience;
    private String food;
    private String drink;
    private boolean isFoodServed;
    private boolean isDrinkServed;
    int totalPrice;

    public Customer(String food, boolean isFoodServed, String drink, boolean isDrinkServed, int totalPrice) {
	this.custPatience = 10;
	this.food = food;
	this.isFoodServed = isFoodServed;
	this.drink = drink;
	this.isDrinkServed = isDrinkServed;
	this.totalPrice = totalPrice;
    }

    public int getCustPatience() {
	return custPatience;
    }

    public void setCustPatience(int custPatience) {
	this.custPatience = custPatience;
    }

   

    public String getFood() {
	return food;
    }

    public void setFood(String food) {
	this.food = food;
    }

    public String getDrink() {
	return drink;
    }

    public void setDrink(String drink) {
	this.drink = drink;
    }

    public boolean isFoodServed() {
	return isFoodServed;
    }

    public void setFoodServed(boolean isFoodServed) {
	this.isFoodServed = isFoodServed;
    }

    public boolean isDrinkServed() {
	return isDrinkServed;
    }

    public void setDrinkServed(boolean isDrinkServed) {
	this.isDrinkServed = isDrinkServed;
    }

    public int getTotalPrice() {
	return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
	this.totalPrice = totalPrice;
    }

}
