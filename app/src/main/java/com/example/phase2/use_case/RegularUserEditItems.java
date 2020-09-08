package com.example.phase2.use_case;
import com.example.phase2.entity.*;

/**
 * An instance of this class is a Use Case for regular user adding/removing own wishlist or available list item
 */
public class RegularUserEditItems extends EditItems {

    private Item item;
    private RegularUser user;

    /**
     * Initialize an instance as user case for regular user to add/delete an item from a regular user's
     * wishlist/available list
     * @param item the item object
     * @param user the the regular user
     */
    public RegularUserEditItems(Item item, RegularUser user){
        this.item = item;
        this.user = user;
    }

    /**
     * This method overrides the parent class method and allows regular user to add an item to wishlist
     */
    @Override
    public void addToWishList() {
        this.user.execute("Undo adding item to wishList.");
        this.user.addWillingToBorrowItem(this.item);
    }

    /**
     * This method overrides the parent class method and allows regular user to remove an item from wishlist
     */
    @Override
    public void removeFromWishList() {
        this.user.execute("Undo removing item from wishList.");
        this.user.removeFromWillingToBorrowItem(this.item);
    }

    /**
     * This method overrides the parent class method and allows regular user to add an item to available list
     */
    @Override
    public void addToAvailableList() {
        this.user.execute("Undo adding item to availableList.");
        this.item.setUsername(this.user.getUsername());
        this.user.getWillingToLendItems().add(this.item);
    }

    /**
     * This method overrides the parent class method and allows regular user to remove an item from available list
     */
    @Override
    public void removeFromAvailableList() {
        this.user.execute("Undo removing item from availableList.");
        this.user.getWillingToLendItems().remove(this.item);
    }


}
