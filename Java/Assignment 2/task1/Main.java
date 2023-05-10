package task1;

import task1.model.Fraction;

public class Main {

    public static void main(String[] args){
        Fraction f1 = new Fraction(1,2);
        Fraction f2 = new Fraction(5, 6);

        System.out.println("Aufgabe 1a): ");
        System.out.println(f1.str());
        System.out.println(f2.str());


        System.out.println("Aufgabe 1b): ");
        Fraction f = new Fraction (3, 6);
        System.out.println(f.str());
        f.setNumerator (4);
        System.out.println(f.str());
        f.setValues (3, 9);
        System.out.println(f.str());

        System.out.println("Aufgabe 1c): ");
        Fraction fa = new Fraction (1, 2);
        Fraction fb = new Fraction (2, 3);
        Fraction fc = Fraction.add(fa , fb);
        System.out.println(fc.str());

        Fraction fm1 = new Fraction (1, 2);
        Fraction fm2 = new Fraction (2, 3);
        Fraction fm3 = Fraction.multiply(fm1 , fm2);
        System.out.println(fm3.str());

    }
}
