package video_rental.items;
/**This Medium class implements a Medium Interface and it create a media.
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class Medium implements MediumI {
    protected double price;
    private String id;
    private String title;
    String mediaType;
    boolean on_sale;
    double sale_price;
    boolean rented;

    /**This is constructor of medium
     * @param id
     * @param  title
     * @param  price
     * @param mediaType
     * */
    public Medium(String id, String title, double price, String mediaType) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.mediaType = mediaType;
        this.on_sale = false;
        this.sale_price = 0.0;
        this.rented = false;
    }
    /**
     * getter for id
     */
    @Override
    public String getID() {
        return this.id;
    }

    /**
     * setter for ID
     *
     * @param id
     */
    @Override
    public void setID(String id) {
        this.id = id;
    }

    /**
     * getter for title
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * setter for title
     *
     * @param title
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for price
     */
    @Override
    public double getPrice() {
        if (this.on_sale) {
            return this.sale_price;
        } else {
            return this.price;
        }
    }

    /**
     * setter for price
     *
     * @param price
     */
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * setter for sale price
     *
     * @param sale_price
     */
    @Override
    public void setSalePrice(double sale_price) {
        this.sale_price = sale_price;
        this.on_sale = true;
    }

    /**
     * check if item on sale
     */
    @Override
    public boolean isOnSale() {
        return false;
    }

    /**
     * setter for on sale
     *
     * @param sale
     */
    @Override
    public void setOnSale(boolean sale) {
        on_sale = sale;
    }

    /**
     * getter movie type
     */
    @Override
    public MovieGenre getMovieType() {
        return null;
    }

    /**
     * getter for media type
     */
    @Override
    public String getMediaType() {
        return this.mediaType;
    }

    /**
     * tells that item is rented or not
     */
    @Override
    public boolean isRented() {
        return false;
    }

    /**
     * remove sale price
     */
    @Override
    public void removeSalePrice() {
        this.sale_price = 0.0;
        this.on_sale = false;
    }

    /**
     * checks if customer can buy
     *
     * @param age
     */
    @Override
    public boolean isAllowed(int age) {
        return age > 18;
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
        return null;
    }

    /**
     * checks if customer can buy this media.
     *
     * @param age
     */
    @Override
    public boolean canBeRentedByAge(int age) {
        return false;
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
        return null;
    }

    /**
     * getter for artist
     */
    @Override
    public String getArtist() {
        return null;
    }

    /**
     * setter for artist
     *
     * @param artist
     */
    @Override
    public void setArtist(String artist) {

    }

    /**
     * getter for name
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Setter for name
     *
     * @param name
     */
    @Override
    public void setName(String name) {

    }

    @Override
    public String toString(){
        return String.format("[%s: %s, %g]", this.mediaType, this.getTitle(), this.getPrice());
    }
}
