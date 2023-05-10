package video_rental.items;
/**This class is main class for other class like DVD, CD and BluRay class, its have all variables and methods which are needed to create a objekt
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class Medium {
    /**Those variables are to help create a DVD, CD or BluRay
     * which has title, price, sale price, on sale or not, rented or not, CD + DVD ID and the type of medium*/
    private String title;
    private double price;
    private double sale_price;
    private boolean on_sale;
    private boolean rented;
    private String ID;
    private String mediumType;

    /**This methode is constructor for all DVD's and CD's, everyone of those musst have title, price and ID
     * @param title type String and its title of medium
     * @param price type double and its a price of medium
     * @param ID type String and its ID of medium
     * */
    public Medium(String title, double price, String ID){
        this.title = title;
        this.price = price;
        this.sale_price = 0.0;
        this.on_sale = false;
        this.rented = false;
        this.ID = ID;
        this.mediumType = "";
    }

    /**Getter for title*/
    public String getTitle() {
        return title;
    }

    /**Setter for title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**This methode is first to check if is on sale or not then give the price of it
     * @return price of medium
     * */
    public double getPrice() {
        if (this.on_sale) {
            return this.sale_price;
        } else {
            return this.price;
        }
    }

    /**This methode is to check which type is the chosen medium
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @return DVD , CD or BluRay
     * */
    public String getMediumType(){
        if (this.mediumType.equalsIgnoreCase("DVD")){
            return "DVD";
        }else if (this.mediumType.equalsIgnoreCase("CD")){
            return "CD";
        }else if (this.mediumType.equalsIgnoreCase("BLURAY")){
            return "BluRay";
        }

        return "";
    }

    /**Setter for price*/
    public void setPrice(double price) {
        this.price = price;
    }

    /**This methode is to check if this stuff is already rented or not
     * @return boolean yes or no
     * */
    public boolean isRented() {
        return this.rented;
    }

    /**This methode is to set chosen medium to rented*/
    public void rent() {
        this.rented = true;
    }

    /**This methode to set chosen medium to not rented*/
    public void ret() {
        this.rented = false;
    }

    /**This methode is to set sale price on chosen medium, and its checks if its BluRay or not.
     * Because BluRay medium can only have 20% discount
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param sale_price sale price that will replace the original price
     * */
    public void setSalePrice(double sale_price) {
        if (getMediumType().equalsIgnoreCase("BLURAY")){
            double result = this.price * 0.2;
            double lastPrice = this.price - result;
            if (sale_price < lastPrice){
                System.out.println("\nSorry this is brand new movie, max discount on this moive is 20% of the original price!");
                System.out.printf("So last price will be %.2f EUR\n", lastPrice);
                this.sale_price = lastPrice;
                this.on_sale = true;
                return;
            }else{
                this.sale_price = sale_price;
                this.on_sale = true;
            }
        }
        this.sale_price = sale_price;
        this.on_sale = true;
    }

    /**This methode is to remove sale price*/
    public void removeSalePrice() {
        this.sale_price = 0.0;
        this.on_sale = false;
    }

    /**Getter for ID*/
    public String getID() {
        return ID;
    }

    /**Setter for ID*/
    public void setID(String ID) {
        this.ID = ID;
    }

    /**This methode is overwritten and it checks which meidum type first then prints the format
     * @return string format */
    @Override
    public String toString(){
        if (getMediumType().equalsIgnoreCase("DVD")){
            return String.format("DVD: %s, Price: %.2f, IMDb ID: [%s]\n", getTitle(), getPrice(), getID());
        }else if(getMediumType().equalsIgnoreCase("CD")){
            return String.format("CD: %s, Price: %.2f, MBID ID: [%s]\n", getTitle(), getPrice(), getID());
        }
        return String.format("You bought some thing that not DVD or CD with title: %s, Price: %.2f and has this ID: [%s]", getTitle(), getPrice(), getID());
    }
}
