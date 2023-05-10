package com.example.money_split_f1.Adapters;

/**this interface contains three methods to help adapters to be clickable
 *
 * @author Anas Salameh
 * */
public interface RecyclerViewClickListener {
    void ItemClicked(int pos);
    void ItemClicked(int pos, String notificationId);
    void ItemClicked();
}
