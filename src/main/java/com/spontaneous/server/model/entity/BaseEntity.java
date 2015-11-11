package com.spontaneous.server.model.entity;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Created by USER1 on 09/11/2015.
 */

@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Id of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected long id;

    /**
     * When the entity was created.
     */
    @Column(name = "creation_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime creationTime;

    /**
     * Create a new empty entity.
     */
    public BaseEntity() {
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
     * @return whether the given entity is equal to this entity.
     */
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        BaseEntity that = (BaseEntity) obj;

        return getId() == that.getId();
    }
}
