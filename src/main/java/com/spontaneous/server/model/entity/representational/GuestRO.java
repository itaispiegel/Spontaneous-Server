package com.spontaneous.server.model.entity.representational;

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

        this.items = new ArrayList<>(items.size());
        this.items.addAll(items.stream()
                .map(Item::createRepresentationalObject)
                .collect(Collectors.toList()));
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
