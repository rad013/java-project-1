package main;

public enum Suit {
    heart("♥"), diamond("♦"), spade("♠"), club("♣");

    private  String display;

    Suit(String display) {
	this.display = display;
    }

    public String getDisplay() {
	return display;
    }

    @Override
    public String toString() {
	return display;
    }
}
