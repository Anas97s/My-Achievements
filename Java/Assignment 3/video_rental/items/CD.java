package video_rental.items;
/**
 * CD class implements all about CD, like for title, price, offer and discount price
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public class CD {
    /**setting up title, rental price , offer ,discount price, allowed variable*/
    private String title;
    private double rentalPrice;
    private boolean offer;
    private double discountPrice;
    private boolean allowed;
    MusicGenre musicGenre;


    /**This Methode creates CD-call-methode
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param  titel_ type String and its for title name
     * @param rentalPrice_ type double and its for price of that DVD
     * */
    public CD(String titel_, double rentalPrice_, MusicGenre musicGenre_, boolean explicitLabel){
        title = titel_;
        rentalPrice = rentalPrice_;
        offer = false;
        this.discountPrice = 0.0;
        musicGenre = musicGenre_;
        allowed = explicitLabel;
    }

    /**Setter for title
     * @param titel_ type String and its for Title name
     * */
    public void setTitle(String titel_){
        title = titel_;
    }

    /**Getter for title
     * @return titel
     * */
    public String getTitel(){
        return this.title;
    }

    /**This methode is to get price of CD and checks if that CD has an offer or not
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @return discountPrice if that CD has an offer on it, if not the returns normal price
     * */
    public double getPrice(){
        if (offer){ //offer = true, means that this CD has an offer price over rental price!
            return this.discountPrice;
        }else{
            return this.rentalPrice;
        }
    }

    /**Setter for rental price
     * @param rentalPrice_ to set new Price for that CD
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
    /**This methode is if there was a dicount price on CD and we dont want to have that offer anymore
     * its removes offer from wanted CD
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * */
    public void removeDiscountPrice(){
        this.discountPrice = 0.0;
        this.offer = false;
    }

    /**
     * this methode is to check if customer can buy this CD or not
     * @param  age type int and its means age of customer
     * @return boolean true if customer is allowed to buy and false if not allowed
     * */
    public boolean isAllowed(int age){
       if (isAllowed() && age < 18){
           System.out.println("Sorry!, You are not allowed to buy this because of your age!\n");
           return false;
       }
       return true;
    }

    /**Getter for Music genre*/
    public MusicGenre getMusicGenre(){
        return this.musicGenre;
    }
    /**This methode prints the CD format with title name and the price
     * @return name with price of CD
     * */
    public String str(){
        return "[CD: " + this.title + ", " + this.getPrice() + "EUR" + ", " +"Music Type: " + getMusicGenre() + "]";
    }

    /**Getter for allowed*/
    public boolean isAllowed() {
        return allowed;
    }
}
