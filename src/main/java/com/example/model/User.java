package com.example.model;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;

/**
 * This class represents a user persisted in the database.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Id of the user.
     */
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    /**
     * The name of the user.
     */
    private String name;

    @Column(name = "email")
    /**
     * The email of the user.
     */
    private String email;

    @Column(name = "profile_picture")
    /**
     * Profile picture URL of the user.
     */
    private URL profilePicture;

    @Column(name = "birthday")
    /**
     * Birthday of the user.
     */
    private Date birthday;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(URL profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePicture=" + profilePicture +
                ", birthday=" + birthday +
                '}';
    }
}
