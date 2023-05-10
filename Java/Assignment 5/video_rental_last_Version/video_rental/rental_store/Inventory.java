package video_rental.rental_store;

import video_rental.items.MediumI;

/**
 *Inventory class to create group of CD's and DVD's and helps to add them separate to group
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class Inventory {
    MediumI[] media;

    /**inventory constructor*/
    public Inventory(){
        this.media = new MediumI[30];
    }

    /**methode to add media to inventory
     * @param medium
     * */
    public void addMedium(MediumI medium) {
        for (int i = 0; i < this.media.length; i++) {
            if (this.media[i] == null) {
                this.media[i] = medium;
                break;
            }
        }
    }

    /**
     * getter for media
     * */
    public MediumI[] getMedia() {
        return this.media;
    }

    /**methode to print what is in inventory*/
    @Override
    public String toString() {
        String result = "Inventar:\n\n";
        for (int i = 0; i < this.media.length; i++) {
            if (this.media[i] != null) {
                result += this.media[i] + "\n";
            }
        }
        return result;
    }
}
