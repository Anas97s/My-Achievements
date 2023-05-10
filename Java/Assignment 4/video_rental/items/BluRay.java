package video_rental.items;

/**This class is to create a BluRay that extends from Medium
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class BluRay extends Medium {

    /**this String is final because we will not change it at all in this class*/
    private final String mediumType;

    /**This methode is a constructor for BluRay and it call the main class throw super
     * @param title BluRay title
     * @param price BluRay price
     * @param ID ID of BluRay
     * */
    public BluRay(String title, double price, String ID){
        super(title,price,ID);
        this.mediumType = "BluRay";
    }

    /**Getter for title*/
    public String getTitle(){
        return super.getTitle() + " BluRay";
    }

    /**Getter for Type*/
    public String getMediumType(){
        return this.mediumType;
    }

    /**This methode is overwritten and  prints the format
     * @return string format */
    @Override
    public String toString(){

        return String.format("[DVD: %s, %.2f EUR with IMDb ID: [%s] ]\n", this.getTitle(), this.getPrice(), this.getID());
    }
}
