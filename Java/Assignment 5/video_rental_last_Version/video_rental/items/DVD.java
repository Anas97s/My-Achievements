package video_rental.items;

import static java.lang.Math.max;
/**
 * DVD class implements all about DVD, like for title, price, offer and discount price and its extends medium
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class DVD extends Medium{
    /**setting up fsk rating and movie genre*/
    private FSKRaiting fsk;
    private MovieGenre movieGenre;

    /**constructor for BluRay
     * @param id
     * @param title
     * @param price
     * @param fsk
     * @param genre
     * */
    public DVD(String id, String title, double price, FSKRaiting fsk, MovieGenre genre) {
        super(id,title, price, "DVD");
        this.movieGenre = genre;
        this.fsk = fsk;
    }

    /**
     * getter movie type
     */
    @Override
    public MovieGenre getMovieType() {
        return this.movieGenre;
    }

    /**
     * checks if customer can buy this media.
     *
     * @param age
     */
    @Override
    public boolean canBeRentedByAge(int age) {
        return this.fsk.ageValid(age);
    }

    /**
     * rent methode
     */
    @Override
    public void rent() {
        this.rented = true;
    }

    /**
     * return items methode
     */
    @Override
    public void ret() {
        this.rented = false;
    }

    /**
     * getter for fsk rating
     */
    @Override
    public FSKRaiting getFSK() {
        return this.fsk;
    }

    @Override
    public String toString(){
        return String.format("[DVD: %s, %.2f EUR, IMDb ID: %s]", getTitle(), getPrice(), getID());
    }
}
