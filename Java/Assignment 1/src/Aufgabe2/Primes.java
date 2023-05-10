package Aufgabe2;

public class Primes {
    static String intArrayToString(int[] arr){
        String s = "";
        s = s + "[";
        for (int i = 0; i < arr.length; i++) {
            s = s + arr[i];
            if (i + 1 != arr.length)
                s = s + ", ";
        }
        s = s + "]";
        return s;
    }

    static String boolArrayToString(boolean[] arr){
        String s = "";
        s = s + "[";
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == true){
                s = s + "t";
            }else if (arr[i] == false){
                s = s + "f";
            }

            if (i + 1 != arr.length){
                s = s + ", ";
            }
        }

        s = s + "]";
        return s;
    }

    public static boolean[] fillArray(int n){
        boolean[] arr = new boolean[n + 1];
        for (int i = 0; i <= n; i++){
            if (i < 2){
                arr[i] = false;
            }else{
                arr[i] = true;
            }
        }
        return arr;
    }
    public static void filterArray(boolean[] arr){
        int index = 0;
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == true){
                index = i;
                index += 2;
                while(index < arr.length){
                    arr[index] = false;
                    index += 2;
                }
                break;
            }
        }
    }

    public static int[] primes(int n){
        boolean[] arr = fillArray(n);
        filterArray(arr);
        int counter = 0; // count number of prime numbers in array
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == true){
                counter++;
            }
        }
        int[] primes = new int[counter]; //create new array for prime numbers only!
        int index = 0; //
        for (int i = 0; i < arr.length; i++){//go throw array and look for true only then put that index number to primes array
            if (arr[i] == true){
                primes[index] = i;
                index++;
            }
        }

        return  primes;
    }

    public static void main(String[] args){
        int[] bspInt = {1,2,3,4,5};
        boolean [] bspBool = {true , false , true , false , true};
        System.out.println("Aufgabe 2a) :");
        System.out.println(intArrayToString(bspInt));
        System.out.println(boolArrayToString(bspBool));

        System.out.print("\n");
        System.out.println("Aufgabe 2b) :");
        boolean [] arr = fillArray (10);
        System.out.println(boolArrayToString(arr));

        System.out.print("\n");
        System.out.println("Aufgabe 3b) :");
        boolean [] arr1 = fillArray (20);
        filterArray(arr1);
        System.out.println(boolArrayToString(arr1));

        System.out.print("\n");
        System.out.println("Aufgabe 3c) :");
        int[] arr2 = primes (20);
        System.out.println(intArrayToString(arr2));
    }

}
