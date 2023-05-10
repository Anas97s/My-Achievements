package video_rental;

import video_rental.items.*;
import video_rental.rental_store.Customer;
import video_rental.rental_store.Inventory;

import java.util.Scanner;

/**
 * Main class to run this Programm
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        DVD d1 = new DVD("tt-150-250", "SpiderMan ", 8.5, FSKRaiting.FSK12, MovieGenre.ACTION);
        DVD d2 = new DVD("tt-579-232", "NoPain", 4.5, FSKRaiting.FSK12, MovieGenre.COMEDY);
        DVD d3 = new DVD("tt-971-520", "TheRide", 2.5, FSKRaiting.FSK12, MovieGenre.ACTION);


        CD c1 = new CD("657950", "Lalo", "Lila", 2.70, true, MusicGenre.CLASSIC);
        CD c2 = new CD("651278","Timoa", "Wizz", 3.78, false, MusicGenre.BLUES);

        BluRay b1 = new BluRay("nm-652-741", "BatMan", 10.0, FSKRaiting.FSK12, MovieGenre.ACTION);
        BluRay b2 = new BluRay("nm-520-650", "TheLion", 5.45, FSKRaiting.FSK0, MovieGenre.COMEDY);


        Inventory shopInventory = new Inventory();
        shopInventory.addMedium(d1);
        shopInventory.addMedium(d2);
        shopInventory.addMedium(d3);
        shopInventory.addMedium(c1);
        shopInventory.addMedium(c2);
        shopInventory.addMedium(b1);
        shopInventory.addMedium(b2);



        System.out.println("Please enter your First name: ");
        String firstName = input.nextLine();
        System.out.println("Please enter your Last name: ");
        String lastName = input.nextLine();
        System.out.println("Please enter your age: ");
        int age = input.nextInt();
        System.out.println("Please enter your balance: ");
        double money = input.nextDouble();
        System.out.println("Hello " + firstName + " " + lastName + " " + age + " Old" + ", Welcome to the Shop. You have " + money + " EUR as a Credit, Enjoy!");
        System.out.print("This is inventory of Shop ");
        System.out.println(shopInventory.toString());

        Customer customer = new Customer(firstName,lastName,age,money);

        String option = "";
        while(!option.equalsIgnoreCase("Q")){
            System.out.println("Rent, (R)eturn, (S)how,  Q to exit");
            option = input.nextLine();
            if (option.equalsIgnoreCase("RENT")) {
                System.out.println("What do you want to rent? DVD, BluRay or CD");
                String rent = input.nextLine();
                if (rent.equalsIgnoreCase("DVD") || rent.equalsIgnoreCase("BLURAY")) {
                    System.out.println("Please enter name of chosen item: ");
                    String itemName = input.nextLine();
                    if (itemName.equalsIgnoreCase("SPIDERMAN")) {
                        if (money < d1.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(d1);
                        money -= d1.getPrice();
                        System.out.println("Your credit now is : " + money);

                    } else if (itemName.equalsIgnoreCase("NOPAIN")) {
                        if (money < d2.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(d2);
                        money -= d2.getPrice();
                        System.out.println("Your credit now is : " + money);
                    } else if (itemName.equalsIgnoreCase("THERIDE")) {
                        if (money < d3.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(d3);
                        money -= d3.getPrice();
                        System.out.println("Your credit now is : " + money);
                    } else if (itemName.equalsIgnoreCase("BATMAN")) {
                        if (money < b1.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(b1);
                        money -= b1.getPrice();
                        System.out.println("Your credit now is : " + money);
                    } else if (itemName.equalsIgnoreCase("THELION")) {
                        if (money < b2.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(b2);
                        money -= b2.getPrice();
                        System.out.println("Your credit now is : " + money);
                    }
                } else if (rent.equalsIgnoreCase("CD")) {
                    System.out.println("Please enter ID of the chosen CD: ");
                    int cdID = input.nextInt();
                    if (cdID == 657950) {
                        if (money < c1.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(c1);
                        money -= c1.getPrice();
                        System.out.println("Your credit now is : " + money);
                    } else if (cdID == 651278) {
                        if (money < c2.getPrice()) {
                            System.out.println("Sorry You cant buy this, you dont have enough credit! See You later");
                            break;
                        }
                        customer.rent(c2);
                        money -= c2.getPrice();
                        System.out.println("Your credit now is : " + money);
                    }
                }
            }else if (option.equalsIgnoreCase("R")){
                System.out.println("What do you want to return: DVD, BluRay, or CD?: ");
                String itemReturn = input.nextLine();
                if (itemReturn.equalsIgnoreCase("DVD") || itemReturn.equalsIgnoreCase("BLURAY")){
                    System.out.println("Please enter name of chosen item to be returned: ");
                    String returnedItem = input.nextLine();
                    if (returnedItem.equalsIgnoreCase("SPIDERMAN")){
                        customer.returnMedium(d1);
                        System.out.println("You have returned " + d1.getTitle() + " successfully!");
                    }else if (returnedItem.equalsIgnoreCase("NOPAIN")){
                        customer.returnMedium(d2);
                        System.out.println("You have returned " + d2.getTitle() + " successfully!");
                    }else if (returnedItem.equalsIgnoreCase("THERIDE")){
                        customer.returnMedium(d3);
                        System.out.println("You have returned " + d3.getTitle() + " successfully!");
                    }else if (returnedItem.equalsIgnoreCase("BATMAN")){
                        customer.returnMedium(b1);
                        System.out.println("You have returned " + b1.getTitle() + "-" + b1.getMediaType() +  " successfully!");
                    }else if (returnedItem.equalsIgnoreCase("THELION")){
                        customer.returnMedium(b2);
                        System.out.println("You have returned " + b2.getTitle() + "-" + b2.getMediaType() +" successfully!");
                    }
                }else if (itemReturn.equalsIgnoreCase("CD")){
                    System.out.println("Please enter ID of chosen CD to be returned: ");
                    int returnedCD = input.nextInt();
                    if (returnedCD == 657950){
                        customer.returnMedium(c1);
                        System.out.println("You have returned " + c1.getTitle() + " successfully!");
                    }else if (returnedCD == 651278){
                        customer.returnMedium(c2);
                        System.out.println("You have returned " + c2.getTitle() + " successfully!");
                    }
                }
            }else if (option.equalsIgnoreCase("S")){
                System.out.print("This is your rented items: ");
                System.out.println(customer.toString());
            }
        }

        System.out.println("Thank Your for visiting our shop! We Hope you enjoyed!");

    }
}

