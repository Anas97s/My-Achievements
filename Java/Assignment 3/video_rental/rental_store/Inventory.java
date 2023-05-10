package video_rental.rental_store;


import video_rental.items.CD;
import video_rental.items.DVD;
/**
 *Inventory class to create group of CD's and DVD's and helps to add them separate to group
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public class Inventory {
    /**creating 2 groups, one for DVDs and one for CDs*/
    DVD[] dvds;
    CD[] cds;

    /**
     * This methode sets up the size of DVDs/CDs group
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * */
    public Inventory() {
        this.dvds = new DVD[10];
        this.cds = new CD[10];
    }

    /**This Methode to add new DVD to DVD's group
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param item DVD item to be added
     * */
    public void addDVD(DVD item) {
        for (int i = 0; i < this.dvds.length; i++) {
            if (this.dvds[i] == null) {
                this.dvds[i] = item;
                break;
            }
        }
    }

    /**This Methode to add new CD to CD's group
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param item CD item to be added
     * */
    public void addCD(CD item) {
        for (int i = 0; i < this.cds.length; i++) {
            if (this.cds[i] == null) {
                this.cds[i] = item;
                break;
            }
        }
    }

    /**Getter for DVD's
     * @return  dvds
     * */
    public DVD[] getDVDs() {
        return this.dvds;
    }
    /**Getter for CD's
     * @return  cds
     * */
    public CD[] getCDs() {
        return this.cds;
    }

    /**
     * This Methode is to print what has been added to Inventory
     * * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @return print what is inside inventory
     * */
    public String str() {
        String result = "Inventar:\n";
        result += "DVDs:\n";
        for (int i = 0; i < this.dvds.length; i++) {
            if (this.dvds[i] != null) {
                result += this.dvds[i].str() + "\n";
            }
        }
        result += "CDs:\n";
        for (int i = 0; i < this.cds.length; i++) {
            if (this.cds[i] != null) {
                result += this.cds[i].str() + "\n";
            }
        }
        return result;
    }
}
