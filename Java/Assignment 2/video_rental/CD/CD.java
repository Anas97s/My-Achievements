package video_rental.CD;

public class CD {
    private String titel;
    private double rentalPrice;
    private boolean offer;
    private double discountPrice; // its between 0.0 and 1.0


    public CD(String titel_, double rentalPrice_, boolean offer_){
        this.titel = titel_;
        this.rentalPrice = rentalPrice_;
        this.offer = offer_;
    }

    public void setTitle(String titel_){
        this.titel = titel_;
    }
    public String getTitel(){
        return this.titel;
    }

    public double getPrice(){
        if (offer){ //offer = true, means that this CD has an offer price over rental price!
            double result = rentalPrice  * discountPrice;
            return this.rentalPrice -= result;
        }else{
            return this.rentalPrice;
        }
    }

    public void setOffer(boolean offer_){
        this.offer = offer_;
    }
    public void setRentalPrice(double rentalPrice_){
        this.rentalPrice = rentalPrice_;
    }
    public void setDiscountPrice(double discountPrice_){
        this.discountPrice = discountPrice_;
    }
    public boolean getOffer(){
        return this.offer;
    }

    public String str(){
        return String.format("[CD: %s, %.2f EUR]", getTitel(), getPrice());
    }

}
