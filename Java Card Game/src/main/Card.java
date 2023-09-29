package main;

public class Card {
    private  Suit suit;
    private  Rank rank;

    public Card(Suit suit, Rank rank) {
	this.suit = suit;
	this.rank = rank;
    }

    public Suit getSuit() {
	return suit;
    }

    public Rank getRank() {
	return rank;
    }

    @Override
    public String toString() {
	return rank.toString() + suit.toString();
    }
}