package video_rental.rental_store;

import video_rental.items.MediumI;

/**This class is to create Customer, so after that can buy stuff from store
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class Customer {
    /**setting Name, age, and customer number */
    private String firstName;
    private String lastName;
    private int age;
    private int customerNumber;
    private MediumI[] media;
    private double money;

    private static int currentMaxCustomerNumber = 0;

    /**constructor to Customer
     * @param firstName first name of customer
     * @param lastName last name of customer
     * @param age age of customer
     * */
    public Customer(String firstName, String lastName, int age, double money) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.customerNumber = ++currentMaxCustomerNumber;
        this.media = new MediumI[30];
        this.money = money;
    }

    /**Getter for first name
     * @return firstName of customer */
    public String getFirstName() {
        return this.firstName;
    }
    /**Getter for last name
     * @return last name of customer*/
    public String getLastName() {
        return this.lastName;
    }

    /**getter for age*/
    public int getAge() {
        return this.age;
    }
    /**getter for customer number*/
    public int getCustomerNumber() {
        return this.customerNumber;
    }

    /**getter for all medium by customer*/
    public MediumI[] getMedia() {
        return this.media;
    }

    /**checks if its rented*/
    public void rent(MediumI medium) {
        if (! medium.canBeRentedByAge(this.age) || medium.isRented())
            System.exit(1);
        for (int i = 0; i < this.media.length; i++) {
            if (this.media[i] == null) {
                this.media[i] = medium;
                medium.rent();
                break;
            }
        }
    }

    /**giving rented mediums back*/
    public void returnMedium(MediumI medium) {
        for (int i = 0; i < this.media.length; i++) {
            if (this.media[i] != null && this.media[i] == medium) {
                this.media[i].ret();
                this.media[i] = null;
                break;
            }
        }
    }

    /**this methode to print what customer has been orderd.*/
    @Override
    public String toString() {
        String result = "Customer " + this.customerNumber + ":" + this.firstName + " " + this.lastName + " (" + this.age + ")" + " rented:\n";
        for (MediumI medium : this.media) {
            if (medium != null) {
                result += medium + "\n";
            }
        }
        return result;
    }
    /**getter for money*/
    public double getMoney() {
        return money;
    }

    /**setter for money*/
    public void setMoney(double money) {
        this.money = money;
    }
}
