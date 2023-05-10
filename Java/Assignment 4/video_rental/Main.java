package video_rental;

import video_rental.items.*;
import video_rental.rental_store.Customer;
import video_rental.rental_store.Inventory;

/**
 * Main class to run this Programm
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 06
 * */
public class Main {
    public static void main(String[] args){
        Inventory inventory = new Inventory();
        Customer customer = new Customer("Anas", "Salameh", 24);
        inventory.addDVD(new DVD("SpiderMan", 4.99, "tt50603", FSKRaiting.FSK6, MovieGenre.ACTION));
        inventory.addDVD(new DVD("BatMan", 6.44, "tt50105", FSKRaiting.FSK6, MovieGenre.ACTION));

        inventory.addCD(new CD("In Rainbows", "Radiohead", 4.66, "20543", false, MusicGenre.CLASSIC));
        inventory.addCD(new CD("Hola", "Wizz", 5.78, "5237", false, MusicGenre.CLASSIC));

        for (int i = 0; i < inventory.getDVDs().length; i++){
            if (inventory.getDVDs()[i] != null){
                customer.rentDVD(inventory.getDVDs()[i]);
            }
        }

        for (int i = 0; i <  inventory.getCDs().length; i++){
            if (inventory.getCDs()[i] != null){
                customer.rentCD(inventory.getCDs()[i]);
            }
        }

        System.out.println(customer.toString() + "\n");

        Medium medium = new Medium("Book", 3.20, "BB789"); //trying to buy something that not CD or DVD
        System.out.println(medium.toString());
        Medium medium1 = new BluRay("The God Father", 10, "tt2021"); //trying to make a discount on BluRay more than 20% of original price!
        medium1.setSalePrice(7.39);
        System.out.println(medium1.toString());



    }
}
