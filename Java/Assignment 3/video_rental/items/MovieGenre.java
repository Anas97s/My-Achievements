package video_rental.items;

public enum MovieGenre { ACTION ("Action"), COMEDY("Comedy"), HORROR("Horror"), ROMANTIC("Romantic");

    private String genre;

    private MovieGenre(String genre_){
        this.genre = genre_;
    }

    private String getGenre() {
        return this.genre;
    }

    public String str(){
        return "This Movie is: " + getGenre();
    }

}
