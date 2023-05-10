package video_rental.items;

/**
 * DVD class implements all about DVD, like for title, price, offer and discount price
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class DVD extends Medium {
    /**setting up rating, genre and ID for DVDs*/
    private FSKRaiting rating;
    private MovieGenre genre;
    /**setting type of this class to DVD*/
    private final String mediumType;



    /**This Methode creates DVD-call-methode
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param  title type String and its for title name
     * @param price type double and its for price of that DVD
     * */
    public DVD(String title, double price, String ID, FSKRaiting rating, MovieGenre genre) {
        super(title, price, ID);
        this.rating = rating;
        this.genre = genre;
        this.mediumType = "DVD";

    }

    /**Getter for Movie Genre*/
    public MovieGenre getMovieType(){
        return this.genre;
    }

    /**Getter for FSK Raiting */
    public FSKRaiting getRating() {
        return this.rating;
    }

    /**Getter for Type*/
    public String getMediumType(){
        return this.mediumType;
    }
    /**
     * this methode is to check if customer can buy this DVD or not
     * @param  age type int and its means age of customer
     * @return boolean true if customer is allowed to buy and false if not allowed
     * */
    public boolean isAllowed(int age){
        return this.rating.ageValid(age);
    }
    /**This methode prints the DVD format with title name and the price
     * @return name with price of DVD
     * */

    /**This methode is overwritten and prints the format
     * @return string format */
    @Override
    public String toString(){

        return String.format("[DVD: %s, %.2f EUR with IMDb ID: [%s] ]", this.getTitle(), this.getPrice(), this.getID());
    }

}
