package video_rental.items;
/**
 * This is enum for Music and its have music type
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 28
 * */
public enum MusicGenre { ROCK,
    POP,
    CLASSIC,
    JAZZ,
    BLUES,
    COUNTRY,
    REGGAE,
    HIPHOP,
    RAP,
    ELECTRONIC,
    ALTERNATIVE,
    OTHER;

    /**This methode to print Music genre */
    public String str() {
        switch (this) {
            case ROCK:
                return "Rock";
            case POP:
                return "Pop";
            case CLASSIC:
                return "Classic";
            case JAZZ:
                return "Jazz";
            case BLUES:
                return "Blues";
            case COUNTRY:
                return "Country";
            case REGGAE:
                return "Reggae";
            case HIPHOP:
                return "Hip-Hop";
            case RAP:
                return "Rap";
            case ELECTRONIC:
                return "Electronic";
            case ALTERNATIVE:
                return "Alternative";
            case OTHER:
                return "Other";
            default:
                return "";
        }
    }


}
