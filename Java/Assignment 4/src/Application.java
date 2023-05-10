import maze.PlayField;
import java.util.Scanner;
/**This class is to run the game on console
 *  @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 *  @version 2022 Mai 08
 *  */
public class Application {
    public static void main(String[] args){
        PlayField playField = new PlayField();

        Scanner scanner = new Scanner(System.in);
        int x = playField.getX();
        int y = playField.getY();
        String move = "";
        boolean check = true;
        while (check){
            if (!playField.goalReached()) {
                playField.printField();
                System.out.println("Select one of the following moves: (U)p, (D)own, (L)eft, (R)ight");
                System.out.print("> ");
                move = scanner.nextLine();

                if (move.equalsIgnoreCase("D")) {
                    if (!playField.moveDown()) {
                        System.out.println("You cant go throw wall!\n Game Over!");
                        break;
                    } else {
                        playField.newPosition(x, y++);
                    }
                } else if (move.equalsIgnoreCase("U")) {
                    if (!playField.moveUp()) {
                        System.out.println("You cant go throw wall!\n Game Over!");
                        break;
                    } else {
                        playField.newPosition(x, y--);
                    }
                } else if (move.equalsIgnoreCase("R")) {
                    if (!playField.moveRight()) {
                        System.out.println("You cant go throw wall!\n Game Over!");
                        break;
                    } else {
                        playField.newPosition(x++, y);
                    }
                } else if (move.equalsIgnoreCase("L")) {
                    if (!playField.moveLeft()) {
                        System.out.println("You cant go throw wall!\n Game Over!");
                        break;
                    } else {
                        playField.newPosition(x--, y);
                    }
                }
            }else{
                check = false;
            }
        }
        playField.printField();
        if (!check) {
            System.out.println("Goal reached, Well Played!");
        }
    }
}
