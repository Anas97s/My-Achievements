package video_rental.items;
/**
 * This is enum for FSK Raiting and it has 0, 6, 12, 16, 18 year, so we can check easily, who can buy this film
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public enum FSKRaiting {
    FSK0,
    FSK6,
    FSK12,
    FSK16,
    FSK18;


    /**This methode is to check customers age and if he is allowed to buy this DVD
     * @param age type int and its mention the age of customer
     * @return boolen true if customer can buy this cd or not
     * */
    public boolean ageValid(int age) {
        switch (this) {
            case FSK0:
                return true;
            case FSK6:
                return age >= 6;
            case FSK12:
                return age >= 12;
            case FSK16:
                return age >= 16;
            case FSK18:
                return age >= 18;
            default:
                return false;
        }
    }

    /**This methode to print Music genre */
    public String str() {
        switch (this) {
            case FSK0:
                return "[FSK 0]";
            case FSK6:
                return "[FSK 6]";
            case FSK12:
                return "[FSK 12]";
            case FSK16:
                return "[FSK 16]";
            case FSK18:
                return "[FSK 18]";
            default:
                return "";
        }
    }
}
