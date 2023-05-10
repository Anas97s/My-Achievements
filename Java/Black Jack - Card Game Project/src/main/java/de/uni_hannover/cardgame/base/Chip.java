package de.uni_hannover.cardgame.base;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class Chip {
    private int value;

    //Constructor for Chip (money in game)
    public Chip(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
