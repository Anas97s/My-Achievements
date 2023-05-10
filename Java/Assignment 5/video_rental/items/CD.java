package video_rental.items;
/**
 * CD class implements all about CD, like for title, price, offer and discount price and extends medium interface
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public  class CD extends MediumI{
    private MusicGenre genre;
    private String artist;
    private String name;

    /**constructor for CD
     * @param id
     * @param name
     * @param artist
     * @param price
     * @param isExplicit
     * @param genre*/
    public CD(String id, String name, String artist, double price, boolean isExplicit, MusicGenre genre) {
        super(id, "", price, "CD");
        this.artist = artist;
        this.name = name;
        this.genre = genre;

    }

    /**getter for title*/
    @Override
    public String getTitle() {
        return String.format("%s - %s", this.artist, this.name);
    }

    /**
     * getter movie type
     */
    @Override
    public MovieGenre getMovieType() {
        return null;
    }

    @Override
    public MusicGenre getGenre(){
        return this.genre;
    }

    /**checks for customer age*/
    @Override
    public boolean canBeRentedByAge(int age) {
        return age > 18;
    }

    /**
     * getter for fsk rating
     */
    @Override
    public FSKRaiting getFSK() {
        return null;
    }


    /**methode prints format of CD*/
    @Override
    public String toString() {
        return String.format("[CD: %s, %.2f EUR, MBID: %s]", getTitle(), getPrice(), getID());
    }

}
