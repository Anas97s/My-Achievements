/**This class to run a programm
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 29
 * */
public class Application {
    public static void main(String[] args){
        Product[] products = new Product[5];
        String[] productsName = new String[products.length];
        String[] allProducts;

        Product p1 = new Product("Headset", 5000);
        Product p2 = new Product("Cola", 240);
        Product p3 = new Product("Eis", 90);
        Product p4 = new Product("Wassser", 140);
        Product p5 = new Product("Keyboard", 75850);

        products[0] = p1;
        products[1] = p2;
        products[2] = p3;
        products[3] = p4;
        products[4] = p5;

        productsName[0] = products[0].getName();
        productsName[1] = products[1].getName();
        productsName[2] = products[2].getName();
        productsName[3] = products[3].getName();
        productsName[4] = products[4].getName();


        allProducts = ConsoleInput.getMultipleChoices(productsName, "Pick what you want");

        int total = 0;
        int i = 0;
        while (i < allProducts.length){
            for (Product product : products) {
                if (product.getName().equalsIgnoreCase(allProducts[i])) {
                    total += product.getPrice();
                }
            }
            i++;
        }

        System.out.println("You have to pay " + total + " Cent, please!");

        EuroDenomination[] euroDenominations = EuroDenomination.values();

        String[] moneyInString = new String[euroDenominations.length];
        for (int k = 0; k < moneyInString.length; k++){
            moneyInString[k] = euroDenominations[k].getCentInString();
        }

        String payment = ConsoleInput.getChoice(moneyInString,"How would you like to pay? ");

        int index = 0;

        for (int j = 0; j < euroDenominations.length; j++){
            if (euroDenominations[j].getCentInString().equalsIgnoreCase(payment)){
                index = j;
            }
        }

        EuroDenomination[] change = new EuroDenomination[1];
        change[0] = euroDenominations[index];


        EuroDenomination[] receipt = EuroCalculator.switcher(change, total);
        System.out.println("Thank You, your rest money is: ");
        String s = "";
        for (int j = 0; j < receipt.length; j++){
            s = receipt[i].getCentInString();
            System.out.println(s);
        }

    }
}
