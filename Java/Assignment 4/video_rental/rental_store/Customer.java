package video_rental.rental_store;

import video_rental.items.CD;
import video_rental.items.DVD;
/**This class is to create Customer, so after that can buy stuff from store
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class Customer {
    /**Every customer have first-last name, age, customer number and max number of DVD's and CD's that can be bought at once!*/
    private String firstName;
    private String lastName;
    private int age;
    private int customerNumber;
    private DVD[] dvds;
    private CD[] cds;
    private static int currentMaxCustomerNumber = 0;

    /**This methode is constructor for customer
     * @param firstName first name of customer
     * @param lastName last name of cusotmer
     * @param  age age of customer*/
    public Customer(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.customerNumber = ++currentMaxCustomerNumber;
        this.dvds = new DVD[10]; //max DVDs can be ordered at once are 10
        this.cds = new CD[10]; //max CDs can be ordered at once are 10
    }

    /**Getter for First name */
    public String getFirstName() {
        return this.firstName;
    }

    /**Getter for last name*/
    public String getLastName() {
        return this.lastName;
    }

    /**Getter for Age*/
    public int getAge() {
        return this.age;
    }
    /**Getter for customer number*/
    public int getCustomerNumber() {
        return this.customerNumber;
    }

    /**Getter for DVDs*/
    public DVD[] getDvds() {
        return this.dvds;
    }
    /**Getter for CDs*/
    public CD[] getCds() {
        return this.cds;
    }

    /**This methode is to rent a DVD from a Store.
     * @param dvd rented dvd to be added
     * */
    public void rentDVD(DVD dvd) {
        if (!dvd.isAllowed(this.age) || dvd.isRented())
            System.exit(1);
        for (int i = 0; i < this.dvds.length; i++) {
            if (this.dvds[i] == null) {
                this.dvds[i] = dvd;
                dvd.rent();
                break;
            }
        }
    }

    /**This methode is to rent a CD from a Store.
     * @param cd rented cd to be added
     * */
    public void rentCD(CD cd) {
        if (! cd.ageValid(this.age) || cd.isRented())
            System.exit(1);
        for (int i = 0; i < this.cds.length; i++) {
            if (this.cds[i] == null) {
                this.cds[i] = cd;
                cd.rent();
                break;
            }
        }
    }

    /**This methode is return rented DVD to store
     * @param dvd returned dvd to store
     * */
    public void returnDVD(DVD dvd) {
        for (int i = 0; i < this.dvds.length; i++) {
            if (this.dvds[i] != null && this.dvds[i] == dvd) {
                this.dvds[i] = null;
                this.dvds[i].ret();
                break;
            }
        }
    }

    /**This methode is return rented CD to store
     * @param cd returned cd to store
     * */
    public void returnCD(CD cd) {
        for (int i = 0; i < this.cds.length; i++) {
            if (this.cds[i] != null && this.cds[i] == cd) {
                this.cds[i] = null;
                this.cds[i].ret();
                break;
            }
        }
    }

    /**This methode is overwritten and it prints what has been rented from store.
     * @return string format */
    @Override
    public String toString(){
        String result = "Borrow-Stuff:\n";
        result += "DVDs:\n";
        for (DVD dvd : this.dvds) {
            if (dvd != null) {
                result += dvd.toString() + "\n";
            }
        }
        result += "CDs:\n";
        for (CD cd : this.cds) {
            if (cd != null) {
                result += cd.toString() + "\n";
            }
        }
        return result;
    }

}
