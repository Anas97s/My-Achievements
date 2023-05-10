package video_rental.items;
/**This is interface of Medium
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022. Mai. 22
 * */
public abstract class MediumI {
    /**setting up variables that needed in every media*/
    String id;
    String title;
    double price;
    double sale_price;
    boolean on_sale;
    String mediaType;
    boolean rented;

    /**constructor for mediumI
     * @param id
     * @param title
     * @param price
     * @param mediaType
     * */
    public MediumI(String id, String title, double price, String mediaType) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.sale_price = 0.0;
        this.on_sale = false;
        this.mediaType = mediaType;
        this.rented = false;
    }

    /**getter for id*/
    public String getID(){
        return this.id;
    }

    /**setter for ID*/
    public  void setID(String id){
        this.id = id;
    }

    /**getter for title*/
    public String getTitle() {
        return title;
    }

    /**setter for title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**getter for price*/
    public double getPrice(){
        if (this.on_sale) {
            return this.sale_price;
        } else {
            return this.price;
        }
    }

    /**setter for price*/
    public void setPrice(double price) {
        this.price = price;
    }

    /**setter for sale price*/
    public  void setSalePrice(double sale_price){
        this.sale_price = sale_price;
        this.on_sale = true;
    }

    /**check if item on sale*/
    public  boolean isOnSale(){
        return false;
    }

    /**setter for on sale*/
    public void setOnSale(boolean sale){
        on_sale = sale;
    }

    /**getter movie type*/
    public abstract MovieGenre getMovieType();


    /**getter for media type*/
    public  String getMediaType(){
        return this.mediaType;
    }

    /**tells that item is rented or not */
    public boolean isRented(){
        return this.rented;
    }

    /**remove sale price*/
    public void removeSalePrice(){
        this.sale_price = 0.0;
        this.on_sale = false;
    }

    /**checks if customer can buy*/
    public boolean isAllowed(int age){
        return age > 18;
    }

    /**tells that cd has explicit label or not*/
    public  boolean isExplicit(){
        return false;
    }

    /**getter for music genre*/
    public abstract MusicGenre getGenre();

    /**override methode to print format of chosen media*/
    public abstract String toString();

    /**checks if customer can buy this media.*/
    public abstract boolean canBeRentedByAge(int age);

    /**rent methode */
    public void rent(){
        this.rented = true;
    }

    /**return items methode*/
    public void ret(){
        this.rented = false;
    }

    /**getter for fsk rating*/
    public abstract FSKRaiting getFSK();
}
