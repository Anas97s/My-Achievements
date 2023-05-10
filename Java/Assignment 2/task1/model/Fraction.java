package task1.model;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(int num, int denom){
        setValues(num,denom);
    }

    private void reduce(){
        int largestNumber;
        // checking which is the largest number between numerator and denominator
        if(this.numerator > this.denominator) {
            largestNumber = this.numerator;
        }else {
            largestNumber = this.denominator;
        }

        int divider = 0;
        // looking for the largest number which can both of numerator and denominator divided by.
        while(largestNumber >= 2) {// the largest number have to be bigger/equal than/to 2.
            if(this.numerator % largestNumber == 0 && this.denominator % largestNumber == 0) {
                divider = largestNumber;
                break;
            }
            largestNumber--;//everytime we find the number we go - 1 until there are no numbers that both of numerator and denominator can be divided by.
        }
        //after we found the number we divide both of numerator and denominator to get the result.
        if(divider != 0 && divider != 1) {
            this.numerator = this.numerator / divider;
            this.denominator = this.denominator / divider;
        }
    }

    public int getNumerator(){
        return this.numerator;
    }

    public int getDenominator(){
        return this.denominator;
    }

    public void setNumerator(int num) {
        this.numerator = num;
        reduce();
    }

    public void setDenominator(int denom){
        this.denominator = denom;
        reduce();
    }


    public void setValues(int num, int denom){
        this.numerator = num;
        this.denominator = denom;
        reduce();
    }

    public static Fraction add(Fraction summandA, Fraction summandB){
        int numA = summandA.numerator;
        int denomA = summandA.denominator;
        int numB = summandB.numerator;
        int denomB = summandB.denominator;
        /*
        * numA/denomA + numB/denomB
        *
        * numerator = (numA * denomB) + (numB * denomA)
        * denominator = denomA * denomB
        * */
        Fraction result = new Fraction((numA * denomB) + (numB * denomA), denomA * denomB);

        return result;
    }
    public static Fraction multiply(Fraction multiplicandA, Fraction multiplicandB){
        int numA = multiplicandA.numerator;
        int denomA = multiplicandA.denominator;
        int numB = multiplicandB.numerator;
        int denomB = multiplicandB.denominator;
        /*
         * numA/denomA * numB/denomB
         *
         * numerator = numA * numB
         * denominator = denomA * denomB
         * */
        Fraction result = new Fraction(numA * numB, denomA * denomB);

        return result;
    }

    public String str(){
        return numerator + "/" + denominator;
    }


}
