package aufgabe3;

import aufgabe3.data.DebugData;

public class DebugMain {
    public static void main(String[] args) {
        DebugData a = new DebugData(3.4, 5.6);
        DebugData b = new DebugData(1.0, 1.0);
        // Result should be approx. 5.18
        System.out.println(String.format("The distance between %s and %s is %.2f", //5) %.2f to get two decimal places.
                a.str(), b.str(), a.distance(b)));
    }
}

/*
* line 10: 5) %.2f to get two decimal places.
* */
