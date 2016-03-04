package com.spontaneous.server.model.entity.representational;

import com.spontaneous.server.model.entity.Item;

/**
 * This is a representational object for the {@link Item} entity.
 */
public class ItemRO {

    private final long id;

    private final String title;
    private final boolean isBringing;

    public ItemRO(long id, String title, boolean isBringing) {
        this.id = id;
        this.title = title;
        this.isBringing = isBringing;
    }

    public long getId() {
        return id;
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
