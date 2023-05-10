package Debug;

class Debug { // 1) klasse falsch geschrieben

    // Methode that checks whether a given number is prime
    public static boolean isPrime(int n) {
        // Return false if number is one, zero or negative
        if (n == 1 || n <= 0) { //2) es war n <= 2
            return false;
        }
        // Check for all factors smaller and equal to n/2 whether it is a prime factor.
        // If it is a prime factor, return false.
        for (int i = 2; i <= n / 2; i++) { //3) hier soll i++ statt i-- stehen, weil wir von 2 starten und es gibt keine prime Zahl als 0 oder kleiner
            if (n % i == 0) {
                return true;
            }
        }
        // If no prime factor was found, return true.
        return false;
    }


    // Method that prints all numbers in an array that are prime
    public static void printPrime(int[] arr) {
        for (int i = 0; i < arr.length; i++) { //4) i sollte kleiner als array länge und nicht kleiner/gleich ODER i = 1 am anfang
            if (!isPrime(arr[i])) { //5) wenn die Rückgabe false ist, dann ist es eine primeZahl, deswegen sollten wir ! vor Funktion anruf.
                System.out.println(arr[i]);
            }
        }
    }

    //6) main Funktion war nicht geschrieben
    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        printPrime(arr);


        // output should be as follows:
        // 1
        // 3
        // 5
        // 7

    }
}

/*
* 1)Zeile 3: klasse falsch geschrieben *ckass*
*
* 2)Zeile 8:  es war n <= 2
*
* 3)Zeile 13: hier soll i++ statt i-- stehen, weil wir von 2 starten und es gibt keine prime Zahl als 0 oder kleiner
*
* 4)Zeile 25: i sollte kleiner als array länge und nicht kleiner/gleich ODER i = 1 am anfang
*
*
* 6)Zeile 32:  main Funktion war nicht geschrieben
* */
