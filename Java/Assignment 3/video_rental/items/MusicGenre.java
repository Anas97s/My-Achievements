package video_rental.items;
/**
 * This is enum for Music and its have music type
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public enum MusicGenre { CLASSIC ("Classic"), POP ("POP"), JAZZ("JAZZ"), ROCK("ROCK");

    /**setting up two variabls one for genre */
    private String genre;

    /**This methode is to give a mention to out enum variables
     * its private because we dont need to call it from outside this package
     * */
    private MusicGenre(String genre_){
        genre = genre_;
    }

    /**Getter for genre*/
    private String getGenre() {
        return this.genre;
    }


    /**This methode to print Music genre */
    public String str(){
        return "Music Genre from this CD is: " + getGenre();
    }


}
