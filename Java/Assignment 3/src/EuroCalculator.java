
/**This class is to calculate changes, when user buy something and want to pay it, then this class calculates the receipt and tells how much user get
 * changes or if he don't have enough money to buy.
 * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
 * @version 2022 April 29
 * */
public class EuroCalculator {


    /**static array have the changes in*/
    public static EuroDenomination[] changes;

    /**static array that have all possible and available payments values */
    public static EuroDenomination[] possible = EuroDenomination.values();


    /**This methode is to calculates the changes during shopping
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param payed money that has been payed by a user
     * @param boughtInCent total amount converted to Cent
     *
     * @return exit if user has no enough money or gives back all his rest money from receipt
     * */
    public static EuroDenomination[] switcher(EuroDenomination[] payed, int boughtInCent){
        int changesMoney = 0;
        int totalPayedMoney = 0;

        for (EuroDenomination euroDenomination : payed) {
            totalPayedMoney += euroDenomination.getCent();
        }

        if (totalPayedMoney < boughtInCent){
            System.out.println("You don't have enough money to buy these!\n");
            System.exit(1);
        }else{
            changesMoney = totalPayedMoney - boughtInCent;

            int x = possibleChange(changesMoney);
            changes = new EuroDenomination[x]; //setting size of our returns array to size of how many changes to be paid back!

            int c = changes.length - 1;
            int p = possible.length - 1;

            while(changesMoney > 0){
                if (changesMoney >= possible[p].getCent()){
                    changes[c] = possible[p];
                    changesMoney -= possible[p].getCent();
                    c--;
                }else{
                    p--;
                }
            }
        }

        return changes;
    }


    /**This Methode calculates how much changes has to be payed to user.
     * @author Anas Salameh {@literal <}anas.salameh@stud.uni-hannover.de {@literal >}
     * @param changes_ its about changes Money that has to be paid back to user
     * */
    public static int possibleChange(int changes_){
        int counter = 0;
        int p = possible.length - 1;

        while(changes_ > 0){
            if (changes_ >= possible[p].getCent()){
                counter++;
                changes_ -= possible[p].getCent();
            }else{
                p--;
            }
        }

        return counter;
    }
}
