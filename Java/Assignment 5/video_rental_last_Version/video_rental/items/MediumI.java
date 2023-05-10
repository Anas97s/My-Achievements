package video_rental.items;
/**This is interface of Medium
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022. Mai. 22
 * */
public interface MediumI {
    /**getter for id*/
    String getID();

    /**setter for ID*/
    void setID(String id);

    /**getter for title*/
    String getTitle();

    /**setter for title*/
    void setTitle(String title);

    /**getter for price*/
    double getPrice();

    /**setter for price*/
    void setPrice(double price);

    /**setter for sale price*/
    void setSalePrice(double sale_price);

    /**check if item on sale*/
    boolean isOnSale();

    /**setter for on sale*/
    void setOnSale(boolean sale);

    /**getter movie type*/
    MovieGenre getMovieType();


    /**getter for media type*/
    String getMediaType();

    /**tells that item is rented or not */
   boolean isRented();

    /**remove sale price*/
    void removeSalePrice();

    /**checks if customer can buy*/
   boolean isAllowed(int age);

    /**tells that cd has explicit label or not*/
    boolean isExplicit();

    /**getter for music genre*/
    MusicGenre getGenre();

    /**override methode to print format of chosen media*/
    String toString();

    /**checks if customer can buy this media.*/
    boolean canBeRentedByAge(int age);

    /**rent methode */
    void rent();

    /**return items methode*/
    public void ret();

    /**getter for fsk rating*/
    FSKRaiting getFSK();

    /**getter for artist*/
    String getArtist();

    /**setter for artist*/
    void setArtist(String artist);

    /**getter for name*/
    String getName();

    /**Setter for name*/
    void setName(String name);

}
