package video_rental.items;

import java.util.Random;

/**
 * DVD class implements all about DVD, like for title, price, offer and discount price and its extends medium interface
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class DVD extends MediumI {
    /**setting up fsk rating and movie genre*/
    private FSKRaiting fsk;
    private MovieGenre genreMovie;

    /**constructor for DVD
     * @param id
     * @param title
     * @param price
     * @param  fsk
     * @param  genre
     * */
    public DVD(String id, String title, double price, FSKRaiting fsk, MovieGenre genre) {
        super(id, title, price, "DVD");
        this.genreMovie = genre;
        this.fsk = fsk;
    }

    /**
     * Getter for id
     * @return id
     */
    @Override
    public String getID() {
        Random random = new Random();
        int i1 = random.nextInt(9);
        int i2 = random.nextInt(9);
        int i3 = random.nextInt(9);
        int i4 = random.nextInt(9);
        int i5 = random.nextInt(9);
        int i6 = random.nextInt(9);

        return String.format("tt-%d%d%d-%d%d%d",i1,i2,i3,i4,i5,i6);
    }

    /**
     * getter movie type
     */
    @Override
    public MovieGenre getMovieType() {
        return this.genreMovie;
    }

    /**
     * getter for music genre
     */
    @Override
    public MusicGenre getGenre() {
        return null;
    }

    /**checks customer age*/
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


    /**methode print format of DVD */
    @Override
    public String toString() {
        return String.format("[DVD: %s, %.2f EUR, IMDb ID: %s]", getTitle(), getPrice(), getID());
    }

}
