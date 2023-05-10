package de.uni_hannover.cardgame.base;


import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class PlayingField {
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private Phase phase;
    private final ArrayList<Chip> chips;

    //Game Field constructor
    public PlayingField(Player player){
        this.player = player;
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.phase = Phase.CONTINUE;
        this.chips = new ArrayList<>();
    }

    //every round
    public void start(){
        deck.shuffle();

        //dealer take cards from deck
        dealer.drawFromDeck(deck);
        dealer.drawFromDeck(deck);

        //player take cards from deck
        player.drawFromDeck(deck);
        player.drawFromDeck(deck);
    }



    public void addBet(Chip chip){
        chips.add(chip);
    }

    public void deleteBet(){
        chips.clear();
    }

    public int getValueOfBet(){
        return chips.stream().mapToInt(Chip::getValue).sum();
    }

    public Dealer getDealer(){
        return dealer;
    }

    public Deck getDeck(){
        return deck;
    }

    public ArrayList<Chip> getChips(){
        return chips;
    }

    public Phase getPhase(){
        return phase;
    }

    public void hitCheck(){
        if (player.getHandPoints() > 21){
            phase = Phase.PLAYERMAXOUTLOSE;
        }else{
            phase = Phase.CONTINUE;
        }
    }

    public void standCheck(){
        if (dealer.getHandPoints() > 21){
            if (player.getHandPoints() == 21 && player.getHand().size() == 2){
                phase = Phase.PLAYERMAXOUTWIN;
            }else{
                phase = Phase.PLAYERWIN;
            }
        }else if (dealer.getHandPoints() == 21){
            if (player.getHandPoints() == 21 && player.getHand().size() == 2){
                phase = Phase.PLAYERMAXOUTWIN;
            }else if (player.getHandPoints() == 21){
                phase = Phase.DRAW;
            }else{
                phase = Phase.DEALERWIN;
            }
        }else if (dealer.getHandPoints() > player.getHandPoints()){
            phase = Phase.DEALERWIN;
        }else if (dealer.getHandPoints() == player.getHandPoints()){
            phase = Phase.DRAW;
        }else{
            if (player.getHandPoints() == 21 && player.getHand().size() == 2){
                phase = Phase.PLAYERMAXOUTWIN;
            }else{
                phase = Phase.PLAYERWIN;
            }
        }

        playersPrize();
    }

    public String getValueOfBetPrinted(){
        DecimalFormat f = new DecimalFormat("\t$##,###");
        return "Bet: " + f.format(getValueOfBet());
    }

    public void playersPrize(){
        if (getPhase() == Phase.PLAYERMAXOUTWIN){
            player.setCredit(player.getCredit() + player.getBet() * 3);
        }else if (getPhase() == Phase.DRAW){
            player.setCredit(player.getCredit() + player.getBet());
        }else if (getPhase() == Phase.PLAYERWIN){
            player.setCredit(player.getCredit() + player.getBet() * 2);
        }
    }


}
