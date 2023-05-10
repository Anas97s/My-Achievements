package video_rental.items;
/**This Medium class extends a Medium Interface and it create a media.
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 Mai 22
 * */
public class Medium extends MediumI {


    /**This is constructor of medium
     * @param id
     * @param  title
     * @param  price
     * @param mediaType
     * */
    public Medium(String id, String title, double price, String mediaType) {
        super(id, title, price, mediaType);

    }

    /**
     * getter movie type
     */
    @Override
    public MovieGenre getMovieType() {
        return null;
    }

    /**
     * getter for music genre
     */
    @Override
    public MusicGenre getGenre() {
        return null;
    }

    /**
     * checks if customer can buy this media.
     * @param age
     */
    @Override
    public boolean canBeRentedByAge(int age) {
        return false;
    }

    /**
     * getter for fsk rating
     */
    @Override
    public FSKRaiting getFSK() {
        return null;
    }


    /**methode thats print the format of medium type*/
    @Override
    public String toString() {
        return String.format("[%s: %s, %g]", this.mediaType, this.getTitle(), this.getPrice());
    }

}
