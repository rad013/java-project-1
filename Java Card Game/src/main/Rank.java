package main;

public enum Rank {
    two(2, "2"), three(3, "3"), four(4, "4"), five(5, "5"), six(6, "6"), seven(7, "7"), eight(8, "8"), nine(9, "9"),
    ten(10, "10"), jack(10, "J"), queen(10, "Q"), king(10, "K"), ace(11, "A");

    private  int val;
    private  String display;

    Rank(int val, String display) {
	this.val = val;
	this.display = display;
    }

    public int getValue() {
	return val;
    }

    public String getDisplay() {
	return display;
    }

    @Override
    public String toString() {
	return display;
    }
}
