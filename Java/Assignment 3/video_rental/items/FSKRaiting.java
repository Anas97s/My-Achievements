package video_rental.items;
/**
 * This is enum for FSK Raiting and it has 0, 6, 12, 16, 18 year, so we can check easily, who can buy this film
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public enum FSKRaiting {forAll(0) , fromSix(6) , fromTwelve(12), fromSixteen(16) , fromEighteen(18);

    /**setting final rate as variable to our enum*/
    private final int rate;

    /**This methode is to give a mention to out enum variables
     * its private because we dont need to call it from outside this package
     * */
    FSKRaiting(int rate_){
        rate = rate_;
    }

    /**Getter for Rate*/
    public int getRate(){
        return this.rate;
    }

    /**This methode is to check customers age and if he is allowed to buy this DVD
     * @param age type int and its mention the age of customer
     * @return boolen true if customer can buy this cd or not
     * */
    public boolean check(int age){
        if (age < getRate()){
            System.out.println("Sorry!, You are not allowed to buy this because of your age!\n");
            return false;
        }
        return true;
    }

    /**This methode to print Music genre */
    public String str(){
        return "FSK Raiting for this Film is from: "  + getRate() + " Years old.";
    }
}
