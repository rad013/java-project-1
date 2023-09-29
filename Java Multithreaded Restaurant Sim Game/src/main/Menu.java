package main;

public  class Menu {
    private String name;
    private int idk;
    private int price;

    public Menu(String name, int idk, int price) {

	this.name = name;
	this.idk = idk;
	this.price = price;
	
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdk() {
        return idk;
    }

    public void setIdk(int idk) {
        this.idk = idk;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

   
}
