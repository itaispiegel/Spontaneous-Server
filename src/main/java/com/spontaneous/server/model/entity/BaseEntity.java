package com.spontaneous.server.model.entity;

import com.spontaneous.server.util.GsonFactory;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a Base Entity template.
 * Contains id and creation time fields.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * Id of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * When the entity was created.
     */
    @Column(name = "creation_time", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    /**
     * Create a new empty entity.
     */
    BaseEntity() {
        this.creationTime = new DateTime();
    }

    /**
     * @return the id number of the entity.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the entity id number.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return when the entity was created.
     */
    public DateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the time that the entity was created.
     */
    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @param obj to compare.
     * @return Whether the given entity is equal to this entity.
     */
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return !(null == obj || !(obj instanceof BaseEntity))
                && (this == obj || getId() == ((BaseEntity) obj).getId());

    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }
}
