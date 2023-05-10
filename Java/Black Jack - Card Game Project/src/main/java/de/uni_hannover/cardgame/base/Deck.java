package de.uni_hannover.cardgame.base;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/

public class Deck {

    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        Suit[] values = Suit.values();
        for (int i = 0; i < values.length; i++) {
            Suit s = values[i];
            Rank[] ranks = Rank.values();
            for (int j = 0; j < ranks.length; j++) {
                Rank r = ranks[j];
                cards.add(new Card(s, r));
            }
        }
    }


    public void shuffle() {
        Collections.shuffle(this.cards);
    }


    public Card drawCard() {
        if (this.cards.isEmpty()) {
            return null;
        } else {
            return cards.remove(0);
        }
    }
}
