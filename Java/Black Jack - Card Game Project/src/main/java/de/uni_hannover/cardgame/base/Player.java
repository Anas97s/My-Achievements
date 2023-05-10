package de.uni_hannover.cardgame.base;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class Player extends Hand{
  private int credit;
  private int bet;

  public Player(){
      this.credit = 50000;
      this.bet = 0;
  }


  public void placeBet(ArrayList<Chip> chips){
      chips.forEach(chip -> bet += chip.getValue());
      credit -= bet;
  }


  public void doubeBet(){
      credit -= bet;
      bet *= 2;
  }

  public void Hit(Deck deck){
      drawFromDeck(deck);
  }

  public void setCredit(int credit){
      this.credit = credit;
  }

  public void deleteBet(){
      this.bet = 0;
  }

  public int getBet(){
      return bet;
  }

  public int getCredit(){
      return credit;
  }

  public String getCreditPrinted(){
      DecimalFormat f = new DecimalFormat("\t##,###");
      return f.format(credit);
  }
}
