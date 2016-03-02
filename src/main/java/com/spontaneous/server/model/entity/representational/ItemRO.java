package com.spontaneous.server.model.entity.representational;

import com.google.gson.annotations.Expose;
import com.spontaneous.server.model.entity.Item;

/**
 * This is a representational object for the {@link Item} entity.
 */
public class ItemRO {

    @Expose
    private final String title;

    @Expose
    private final boolean isBringing;

    public ItemRO(String title, boolean isBringing) {
        this.title = title;
        this.isBringing = isBringing;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBringing() {
        return isBringing;
    }

    @Override
    public String toString() {
        return "ItemRO{" +
                "title='" + title + '\'' +
                ", isBringing=" + isBringing +
                '}';
    }
}
