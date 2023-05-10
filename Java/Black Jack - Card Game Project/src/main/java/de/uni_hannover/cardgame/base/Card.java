package de.uni_hannover.cardgame.base;

/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public final class Card {

    private Suit suit;
    private Rank rank;

    //global constructor for Card
    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit(){
        return this.suit;
    }

    public Rank getRank(){
        return this.rank;
    }

    public int getPoints(){
        return rank.getPoints();
    }

    public String getImageUrl() {
        String suitCard = suit.name().toLowerCase();
        String rankCard = String.valueOf(rank.ordinal() + 1);
        //path to cards images in resources
        String cardName = "/images/" + suitCard + "_" + rankCard + ".gif";

        return  cardName;
    }

    @Override
    public String toString() {
        return "[" + this.suit + " " + this.rank + "]";
    }
}
