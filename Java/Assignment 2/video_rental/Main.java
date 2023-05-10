package video_rental;

import video_rental.CD.CD;
import video_rental.DVD.DVD;

import java.util.Arrays;

public class Main {
    /*
    * ich habe noch zwei Packages CD und DVD erstellt, ich denke, es ist besser, dass für jede Klasse die wir vielleicht noch erweitern werden,
    * alle dazugehörige klassen in einem Package zusammen stellen
    *
    * ich habe Inventory Klasse in dem obersten Package erstellt, weil es aus beide Klassen DVD und CD besteht*/
    public static void main(String[] args){

        DVD d1 = new DVD("Inglourious Basterds", 2.99, false);
        DVD d2 = new DVD("Spider Man 1", 1.99, false );
        DVD d3 = new DVD("Big Ride 1", 5.50, true);
        DVD d4 = new DVD("Big Ride 2", 8.0, false);
        DVD d5 = new DVD("Walking dead", 15.0, true);
        DVD d6 = new DVD("Spider Man 2", 3.0, false);
        DVD d7 = new DVD("The Godfather 1", 25.49, true);
        DVD d8 = new DVD("The Godfather 2", 22.21, true);
        DVD d9 = new DVD("Scareface", 17.50, true);
        DVD d10 = new DVD("Smile ", 3.49, false);
        DVD d11 = new DVD("It Will not Happen", 0.70, false); //expected to not be printed, because it a number 11!

        CD c1 = new CD("FIFA22", 69.99, true);
        CD c2 = new CD("FIFA19", 15.07, false);
        CD c3 = new CD("Overwatch 1", 40, true);
        CD c4 = new CD("OverWatch 2", 80, false);
        CD c5 = new CD("Valorant", 15, false);
        CD c6 = new CD("LoL", 25, true);
        CD c7 = new CD("Call of Duty", 69.99, true);
        CD c8 = new CD("CS:GO", 7.75, false);
        CD c9 = new CD("PUBG ", 30.29, true);
        CD c10 = new CD("Fortnite", 1.23, true);
        CD c11 = new CD("You Will not WIN", 0.75, true); //expected to not be printed, because it a number 11!


        Inventory inventory = new Inventory();
        inventory.addDVD(d1);
        inventory.addDVD(d2);
        inventory.addDVD(d3);
        inventory.addDVD(d4);
        inventory.addDVD(d5);
        inventory.addDVD(d6);
        inventory.addDVD(d7);
        inventory.addDVD(d8);
        inventory.addDVD(d9);
        inventory.addDVD(d10);
        inventory.addDVD(d11);

        inventory.addCD(c1);
        inventory.addCD(c2);
        inventory.addCD(c3);
        inventory.addCD(c4);
        inventory.addCD(c5);
        inventory.addCD(c6);
        inventory.addCD(c7);
        inventory.addCD(c8);
        inventory.addCD(c9);
        inventory.addCD(c10);
        inventory.addCD(c11);

        inventory.str();





    }
}
