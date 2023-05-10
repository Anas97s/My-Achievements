package video_rental.items;

import static java.lang.Math.max;

/**This class is to create a BluRay that extends from Medium
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class BluRay extends MediumI {
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
    public BluRay(String id, String title, double price, FSKRaiting fsk, MovieGenre genre) {
        super(id, title, price,"BluRay");
        this.movieGenre = genre;
        this.fsk = fsk;
    }

    /**
     * Setter for sale price
     * @param sale_price sale price of medium
     */
    @Override
    public void setSalePrice(double sale_price) {
        this.sale_price = max(sale_price, 0.8 * this.price);
        this.on_sale = true;
    }

    /**
     * getter movie type
     */
    @Override
    public MovieGenre getMovieType() {
        return this.movieGenre;
    }

    /**
     * getter for music genre
     */
    @Override
    public MusicGenre getGenre() {
        return null;
    }

    /**checks if customer can buy this item*/
    @Override
    public boolean canBeRentedByAge(int age) {
        return this.fsk.ageValid(age);
    }

    /**
     * getter for fsk rating
     */
    @Override
    public FSKRaiting getFSK() {
        return this.fsk;
    }


    /**methode to print format of BluRay*/
    @Override
    public String toString() {
        return String.format("[BluRay: %s, %.2f EUR, IMDb ID: %s]", getTitle(), getPrice(), getID());
    }
}
