package video_rental.items;
/**
 * CD class implements all about CD, like for title, price, offer and discount price
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class CD extends Medium{
    /**setting up rating and genre variable*/
    private boolean rating;
    private MusicGenre genre;

    /**setting type of this class to CD and name for CD*/
    private String name;
    private final String mediumType;


    /**This Methode creates CD-call-methode
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param  artist type String and its for artist name
     * @param price type double and its for price of that DVD
     * @param name name of CD
     * @param ID MBID ID of CD
     * @param isExplicit checks if it has explicit lable or not
     * @param genre CD's genre type*/
    public CD(String name, String artist, double price, String ID, boolean isExplicit, MusicGenre genre) {
        super(artist, price, ID);
        this.rating = isExplicit;
        this.genre = genre;
        this.name = name;
        this.mediumType = "CD";
    }

    /**Getter for name*/
    public String getName(){
        return this.name;
    }
    /**Getter for title*/
    public String getTitle(){
        return super.getTitle() + " - " + getName();
    }
    /**Getter for Type*/
    public String getMediumType(){
        return this.mediumType;
    }
    /**
     * this methode is to check if customer can buy this CD or not
     * @return boolean true if customer is allowed to buy and false if not allowed
     * */
    public boolean isExplicit() {
        return this.rating;
    }

    /**Getter for Music genre*/
    public MusicGenre getGenre() {
        return this.genre;
    }

    /**This methode is to check if the customer can buy this CD
     * @param age age of customer
     * */
    public boolean ageValid(int age) {
        if (this.rating) {
            return age >= 18;
        } else {
            return true;
        }
    }

    /**This methode prints the CD format with title name and the price
     * @return name with price of CD
     * */
    @Override
    public String toString(){

      return  String.format("[CD: %s, %.2f EUR with MBID ID: [%s] ]", this.getTitle(), this.getPrice(), this.getID());
    }



}
