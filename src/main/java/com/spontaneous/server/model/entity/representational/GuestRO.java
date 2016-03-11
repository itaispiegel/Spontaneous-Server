package com.spontaneous.server.model.entity.representational;

import com.spontaneous.server.model.entity.Guest;
import com.spontaneous.server.model.entity.Item;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.util.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a representational object for the {@link Guest} entity.
 */
public class GuestRO {

    private final long id;

    private final UserProfileRO userProfile;
    private final String status;
    private final boolean isAttending;
    private final List<ItemRO> items;

    public GuestRO(long id, User user, String status, boolean isAttending, List<Item> items) {
        this.id = id;
        this.userProfile = user.createUserProfile();
        this.status = status;
        this.isAttending = isAttending;

        //In case that there are no items.
        if (items == null) {
            this.items = new ArrayList<>();
            return;
        }

        //Add all items to the guest object.
        this.items = Converter.convertList(items, Item::createRepresentationalObject);
    }

    public long getId() {
        return id;
    }

    public UserProfileRO getUserProfile() {
        return userProfile;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAttending() {
        return isAttending;
    }

    public List<ItemRO> getItems() {
        return items;
    }
}
