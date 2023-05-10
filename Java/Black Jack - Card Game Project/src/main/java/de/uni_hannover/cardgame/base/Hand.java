package de.uni_hannover.cardgame.base;


import java.util.ArrayList;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class Hand {
    private ArrayList<Card> hand;

    public Hand(){
        hand = new ArrayList<>();
    }

    public void drawFromDeck(Deck deck){
        Card card = deck.drawCard();
        if (card != null){
            hand.add(card);
        }
    }



    public int getHandPoints(){
        int points = 0;
        int ace = 0;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            points += c.getPoints();
            if (c.getRank() == Rank.ACE){
                ace++;
            }
        }
        //check for ace's and points value
        while(ace-- > 0){
            if (points > 21){//if points is more than 21 and there is a ace card in Hand, reduce points value 10 so player don't max out
                points -= 10;
            }
        }

        return points;
    }

    //every new round player musst have no cards in hand
    public void removeHand(){
        hand.clear();
    }


    public ArrayList<Card> getHand(){
        return hand;
    }
}

