package maze;
/**This class is to create a play field and set the movement
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 08
 * */
public class PlayField {
    /**those are x and y coordinate*/
    int x;
    int y;

    /**setting variables for game field, wall, ground , player and goal*/
    private final String[][] fieldFigur;
    private final String wall = "#";
    private final String ground = " ";
    private final String player = "P";
    private final String goal = "G";

    /**This methode is to create a game field with 5 x 4
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * */
    public PlayField(){
        this.fieldFigur = new String[5][4];
        field(5, 4);
    }

    /**This methode is to create a fild and exact position of walls, ground , player and goal
     * @param colums colums
     * @param rows rows
     * */
    private void field(int colums, int rows) {
        for (int i = 0; i < colums; i++){
            for (int j = 0; j < rows; j++){
                if (i == 0){
                    this.fieldFigur[i][j] = this.wall;
                }else if(i == colums - 1 ){
                    this.fieldFigur[i][j] = this.wall;
                }else if (j == 0){
                    this.fieldFigur[i][j] = this.wall;
                }else if (j == rows - 1){
                    this.fieldFigur[i][j] = this.wall;
                }else{
                    this.fieldFigur[i][j] = this.ground;
                }


            }
        }
        this.fieldFigur[2][2] = this.wall;
        this.fieldFigur[1][2] = this.player;
        this.fieldFigur[3][2] = this.goal;
        setX(1);
        setY(2);
    }

    /**This methode is to print game field
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * */
    public void printField(){
        for (int i = 0; i < this.fieldFigur[0].length; i++){
            for (String[] strings : this.fieldFigur) {
                System.out.print(strings[i]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**This methode is to check if goal is reached or not
     * */
    public boolean goalReached(){
        return getX() == 3 && getY() == 2;
    }

    /**Getter for x*/
    public int getX(){
        return this.x;
    }
    /**Getter for Y*/
    public int getY(){
        return this.y;
    }
    /**Setter for X*/
    public void setX(int x){
        this.x = x;
    }
    /**Setter for Y*/
    public  void setY(int y){
        this.y = y;
    }

    /**this methode to check if player can move to up*/
    public boolean moveUp(){
        return !this.fieldFigur[getX()][getY() - 1].equals(this.wall);
    }

    /**this methode to check if player can move to down*/
    public boolean moveDown(){
        return !this.fieldFigur[getX()][getY() + 1].equals(this.wall);
    }
    /**this methode to check if player can move to right*/
    public boolean moveRight(){
        return !this.fieldFigur[getX() + 1][getY()].equals(this.wall);
    }
    /**this methode to check if player can move to left*/
    public boolean moveLeft(){
        return !this.fieldFigur[getX() - 1][getY()].equals(this.wall);
    }

    /**This methode to set new position of player after moving on game filed
     * @param  x x coordinate
     * @param y y coordinate
     * */
    public void newPosition(int x, int y){
        int oldX = getX();
        int oldY = getY();
        setX(x);
        setY(y);
        this.fieldFigur[getX()][getY()] = this.player;
        this.fieldFigur[oldX][oldY] = this.ground;
    }

}
