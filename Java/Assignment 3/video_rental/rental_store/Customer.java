package video_rental.rental_store;

import video_rental.items.CD;
import video_rental.items.DVD;
import video_rental.items.FSKRaiting;

import java.util.Random;

public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private String customerNM;
    CD[] cds;
    DVD[] dvds;

    public Customer(String firstName_, String lastName_, int age_){
        firstName = firstName_;
        lastName = lastName_;
        age = age_;
        this.customerNM = generateNumber();
        this.cds = new CD[10];
        this.dvds = new DVD[10];
    }


    public String getCustomerNM(){
        return this.customerNM;
    }

    private String generateNumber(){
        Random r = new Random();
        int i1 = r.nextInt(9);
        int i2 = r.nextInt(9);
        int i3 = r.nextInt(9);
        int i4 = r.nextInt(9);
        int i5 = r.nextInt(9);
        int i6 = r.nextInt(9);
        int i7 = r.nextInt(9);
        int i8 = r.nextInt(9);
        int i9 = r.nextInt(9);

        return String.format("%d%d%d%d%d%d%d%d%d", i1, i2, i3, i4, i5, i6, i7, i8, i9);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void borrowDVD(DVD item){
        if (item.isAllowed(getAge())){
            for (int i = 0; i < this.dvds.length; i++){
                if (this.dvds[i] == null){
                    this.dvds[i] = item;
                    break;
                }
            }
        }
    }

    public void borrowCD(CD item){
        if (item.isAllowed(getAge())){
            for (int i = 0; i < this.cds.length; i++){
                if (this.cds[i] == null){
                    this.cds[i] = item;
                    break;
                }
            }
        }
    }

    public DVD[] getBorrowsDVD(){
        return this.dvds;
    }

    public CD[] getBorrowsCD(){
        return this.cds;
    }

    public String str(){
        String result = "Borrow-Stuff:\n";
        result += "DVDs:\n";
        for (DVD dvd : this.dvds) {
            if (dvd != null) {
                result += dvd.str() + "\n";
            }
        }
        result += "CDs:\n";
        for (CD cd : this.cds) {
            if (cd != null) {
                result += cd.str() + "\n";
            }
        }
        return result;
    }

}
