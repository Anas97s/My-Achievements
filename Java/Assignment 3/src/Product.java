/**
 * This class to create a product with name and price
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 29
 * */
public class Product {

    /**name of product*/
    private String name;

    /**price of product*/
    private int price;

    /**This methode is constructor of product
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     *
     * @param  name name of product
     * @param  price price of product
     * */
    public Product(String name, int price){
        this.name = name;
        this.price = price;
    }


    /**Getter for name of product*/
    public String getName() {
        return name;
    }

    /**Getter for price of product*/
    public int getPrice() {
        return price;
    }
}
