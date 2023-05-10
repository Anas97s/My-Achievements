package video_rental.items;

/**
 * DVD class implements all about DVD, like for title, price, offer and discount price
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public class DVD {
    /**setting up title, rental price , offer and discount price variable*/
    private String title;
    private double rentalPrice;
    private boolean offer;
    private double discountPrice;

    private boolean allowed;
    FSKRaiting raiting;
    MovieGenre movieType;

    /**This Methode creates DVD-call-methode
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param  title_ type String and its for title name
     * @param rentalPrice_ type double and its for price of that DVD
     * */
    public DVD(String title_, double rentalPrice_, MovieGenre movieType_, FSKRaiting raiting_){
        title = title_;
        rentalPrice = rentalPrice_;
        offer = false;
        discountPrice = 0.0;
        movieType = movieType_;
        raiting = raiting_;
        allowed = false;
    }

    /**Setter for title
     * @param title_ type String and its for Title name
     * */
    public void setTitle(String title_){
        title = title_;
    }

    /**Getter for title
     * @return titel
     * */
    public String getTitle(){
        return this.title;
    }

    /**This methode is to get price of DVD and checks if that DVD has an offer or not
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @return discountPrice if that DVD has an offer on it, if not the returns normal price
     * */
    public double getPrice(){
        if (offer){ //offer = true, means that this DVD has an offer price over rental price!
            return this.discountPrice;
        }else{
            return this.rentalPrice;
        }
    }

    /**Setter for rental price
     * @param rentalPrice_ to set new Price for that DVD
     * */
    public void setRentalPrice(double rentalPrice_){
        rentalPrice = rentalPrice_;
    }

    /**Setter for discount Price
     * @param discountPrice_ type Double and it to set new discount price
     * */
    public void setDiscountPrice(double discountPrice_){
        discountPrice = discountPrice_;
        this.offer = true;
    }

    /**This methode is if there was a dicount price on DVD and we dont want to have that offer anymore
     * its removes offer from wanted DVD
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * */
    public void removeDiscountPrice(){
        this.discountPrice = 0.0;
        this.offer = false;
    }

    /**Getter for Movie Genre*/
    public MovieGenre getMovieType(){
        return this.movieType;
    }
    /**Getter for FSK Raiting */
    public FSKRaiting getRaiting(){
        return this.raiting;
    }
    /**
     * this methode is to check if customer can buy this DVD or not
     * @param  age type int and its means age of customer
     * @return boolean true if customer is allowed to buy and false if not allowed
     * */
    public boolean isAllowed(int age){
        if (raiting.check(age)){
            allowed = true;
            return true;
        }
        return false;
    }
    /**This methode prints the DVD format with title name and the price
     * @return name with price of DVD
     * */
    public String str(){
        return "[DVD: " + title + ", " + getPrice() + "EUR" + ", " +"Movie Type: " + getMovieType() + ", " + "FSK Raiting: " + getRaiting() + "]";
    }
}
