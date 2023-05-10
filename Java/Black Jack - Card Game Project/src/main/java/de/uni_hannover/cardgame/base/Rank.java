package de.uni_hannover.cardgame.base;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public enum Rank {
    ACE     (" A"),
    TWO     (" 2"),
    THREE   (" 3"),
    FOUR    (" 4"),
    FIVE    (" 5"),
    SIX     (" 6"),
    SEVEN   (" 7"),
    EIGHT   (" 8"),
    NINE    (" 9"),
    TEN     ("10"),
    JACK    (" J"),
    QUEEN   (" Q"),
    KING    (" K");

    private String number;

    Rank(String number){
        this.number = number;
    }

    public int getPoints() {
        switch (this) {
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                return 10;
            case ACE:
                return 11;
        }
        return 0;
    }

    @Override
    public String toString() {
        return number;
    }
}
