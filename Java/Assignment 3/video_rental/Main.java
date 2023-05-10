package video_rental;

import video_rental.items.*;
import video_rental.rental_store.Customer;
import video_rental.rental_store.Inventory;

/**
 * Main class to run this Programm
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public class Main {
    public static void main(String[] args){
        Inventory inventory = new Inventory();
        DVD d1 = new DVD("Spider Man 1", 5.56, MovieGenre.ACTION, FSKRaiting.fromTwelve);
        DVD d2 = new DVD("Big Ride 1", 10.54, MovieGenre.COMEDY, FSKRaiting.fromSix);
        DVD d3 = new DVD("Ted", 1.99, MovieGenre.HORROR, FSKRaiting.fromSixteen);


        CD c1 = new CD("Hop Hop", 2.99, MusicGenre.POP, false);
        CD c2 = new CD("Go Gyal", 3.44, MusicGenre.JAZZ, true);
        CD c3 = new CD("FEFE", 1.44, MusicGenre.POP, false);

        inventory.addDVD(d1);
        inventory.addDVD(d2);
        inventory.addDVD(d3);

        inventory.addCD(c1);
        inventory.addCD(c2);
        inventory.addCD(c3);

        System.out.println(inventory.str());

        Customer customer = new Customer("Max", "Rally", 25);

        for (int i = 0; i < inventory.getDVDs().length; i++){
            if (inventory.getDVDs()[i] != null && customer.getBorrowsDVD()[i] == null){
                customer.borrowDVD(inventory.getDVDs()[i]);
            }
        }

        for (int i = 0; i < inventory.getCDs().length; i++){
            if (inventory.getCDs()[i] != null && customer.getBorrowsCD()[i] == null){
                customer.borrowCD(inventory.getCDs()[i]);
            }
        }

        System.out.println(customer.str());
    }
}
