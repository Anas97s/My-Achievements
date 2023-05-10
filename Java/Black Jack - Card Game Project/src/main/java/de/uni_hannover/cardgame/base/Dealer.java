package de.uni_hannover.cardgame.base;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class Dealer extends  Hand{

    /**
     * this methode its for dealer, draw card from deck if his hand is less than 17
     * @param deck
     *
     * */
    public void dealerHit(Deck deck){
        while (getHandPoints() < 17){
            drawFromDeck(deck);
        }
    }
}
