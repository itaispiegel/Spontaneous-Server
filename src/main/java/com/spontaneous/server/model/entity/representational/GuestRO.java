package com.spontaneous.server.model.entity.representational;

import com.google.gson.annotations.Expose;
import com.spontaneous.server.model.entity.Guest;
import com.spontaneous.server.model.entity.Item;
import com.spontaneous.server.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a representational object for the {@link Guest} entity.
 */
public class GuestRO {

    @Expose
    private final UserProfileRO userProfile;

    @Expose
    private final String status;

    @Expose
    private final boolean isAttending;

    @Expose
    private final List<ItemRO> items;

    public GuestRO(User user, String status, boolean isAttending, List<Item> items) {
        this.userProfile = user.createUserProfile();
        this.status = status;
        this.isAttending = isAttending;

        //In case that there are no items.
        if(items == null) {
            this.items = new ArrayList<>();
            return;
        }

        this.items = new ArrayList<>(items.size());
        this.items.addAll(items.stream()
                .map(Item::createRepresentationalObject)
                .collect(Collectors.toList()));
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
