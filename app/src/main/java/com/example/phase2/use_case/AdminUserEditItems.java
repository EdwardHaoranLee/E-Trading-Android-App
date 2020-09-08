package com.example.phase2.use_case;


import com.example.phase2.entity.*;

/**
 * An instance of this class is a Use Case for admin user adding/removing regular user's wishlist or available list item
 */
public class AdminUserEditItems extends EditItems {

    private Item item;
    private RegularUser user;
    private AdminUser admin;

    /**
     * Initialize an instance as user case for admin user to add/delete an item from a regular user's
     * wishlist/available list
     * @param item the item object
     * @param user the the regular user
     * @param admin the admin user
     */
    public AdminUserEditItems(Item item, RegularUser user, AdminUser admin) {
        this.item = item;
        this.user = user;
        this.admin = admin;
    }

    /**
     * This method overrides the parent class method and allows admin user to add an item to regular user's wishlist
     */
    @Override
    public void addToWishList() {
        this.admin.getUserWishList(this.user).add(this.item);
    }

    /**
     * This method overrides the parent class method and allow admin user to delete item from regular user's wishlist
     */
    @Override
    public void removeFromWishList() {
        this.admin.getUserWishList(this.user).remove(this.item);
    }

    /**
     * This method override the parent class method and allowing admin user to add an item to regular user's
     * available list
     */
    @Override
    public void addToAvailableList() {
        this.admin.getUserAvailableList(this.user).add(this.item);
    }

    /**
     * This method override parent class method and allows admin user to remove item from regular user's available list
     */
    @Override
    public void removeFromAvailableList() {
        this.admin.getUserAvailableList(this.user).remove(this.item);
    }


}
