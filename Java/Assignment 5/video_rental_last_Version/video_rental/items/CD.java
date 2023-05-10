package video_rental.items;

import static java.lang.Math.max;
/**
 * CD class implements all about CD, like for title, price, offer and discount price and extends medium
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class CD extends Medium{
    /**setting up fsk rating and movie genre*/
    private MusicGenre musicGenre;
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
        this.name = name;
        this.artist = artist;
        this.musicGenre = genre;
    }


    /**
     * getter for title
     */
    @Override
    public String getTitle() {
        return String.format("%s - %s", this.artist, this.name);
    }



    /**
     * tells that cd has explicit label or not
     */
    @Override
    public boolean isExplicit() {
        return false;
    }

    /**
     * getter for music genre
     */
    @Override
    public MusicGenre getGenre() {
        return this.musicGenre;
    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("[CD: %s, %.2f EUR, MBID: %s]", getTitle(), getPrice(), getID());
    }
}
