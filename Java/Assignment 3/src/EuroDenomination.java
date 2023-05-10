/**
 * This is enum for diffrent type of Euro and Cent
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 29
 * */
public enum EuroDenomination {
    ONECENT(1, "1 Cent"),
    TWOCENT(2, "2 Cent"),
    FIVECENT(5, "5 Cent"),
    TENCENT(10, "10 Cent"),
    TWENTYCENT(20, "20 Cent"),
    FIFTYCENT(50, "50 Cent"),

    ONEEURO(100, "1 Euro"),
    TWOEURO(200, "2 Euro"),

    FIVEEURO(500, "5 Euro"),
    TENEURO(1000, "10 Euro"),
    TWENTYEURO(2000, "20 Euro"),
    FIFTYEURO(5000, "50 Euro"),
    HUNDREDEURO(10000, "100 Euro"),
    TWOHUNDREDEURO(20000, "200 Euro");


    /**cent variable to get value of all Euros in cent only*/
    private int cent;
    /**variable to convert cent value to String*/
    private String centInString;

    /**This methode its a constructor for this enum
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param cent_ type int and its means the cent value
     * @param centInString_ type String to print what is value of cent in word
     * */
    EuroDenomination(int cent_, String centInString_){
        cent = cent_;
        centInString = centInString_;
    }

    /**Getter for cent*/
    public int getCent() {
        return this.cent;
    }

    /**Getter for cent in word*/
    public String getCentInString() {
        return this.centInString;
    }

    /**This methode prints how much did the user payed in Cent*/
    public String str(){
        return "You have paid: " + getCentInString();
    }
}
