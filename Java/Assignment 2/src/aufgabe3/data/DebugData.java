package aufgabe3.data;
import java.lang.Math;

public class DebugData {
    private double x;
    private double y;

    public DebugData(double x, double y) { //1) parameters were int, it have to be double
        this.x = x;
        this.y = y;
    }

    public double distance(DebugData other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2)); //2) was pow but it have to be sqrt
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y; //3) was x but its have to be y, **this is y Getter**
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public String str() {
        return String.format("(%.2f, %.2f)", this.x, this.y); //4) %.2f to get two decimal places.
    }
}

/*
* line 8: 1) parameters were int, it have to be double
*
* line 14: 2) was pow but it have to be sqrt
*
* line 22: 3) was this.x but its have to be y, **this is y Getter**
*
* line 34: 4) %.2f to get two decimal places.
* */
