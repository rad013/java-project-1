package main;

import java.util.ArrayList;

public class Deck {
    private  ArrayList<Card> deck;

    public Deck() {
	Suit[] allSuits = Suit.values();
	Rank[] allRanks = Rank.values();
	this.deck = new ArrayList<Card>(allSuits.length * allRanks.length);
	for (Suit suit : allSuits) {
	    for (Rank rank : allRanks) {
		deck.add(new Card(suit, rank));
	    }
	}
    }
    public Card getDeck(int i) {
        return deck.get(i);
    }
    public String toString() {
        return deck.toString();
    }

}
