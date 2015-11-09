package com.example.model;

import javax.persistence.*;

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
    protected DateTime creationTime;

    public BaseEntity() {
        this.creationTime = new DateTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

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
