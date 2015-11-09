package com.example.model.entity;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * This class represents a user persisted in the database.
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    /**
     * Facebook id of the user.
     */
    @Column(name = "facebook_user_id", unique = true)
    private String facebookUserId;

    /**
     * Facebook token of the user.
     */
    @Column(name = "facebook_token")
    private String facebookToken;

    /**
     * The name of the user.
     */
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    /**
     * The email of the user.
     */
    private String email;

    /**
     * Profile picture URL of the user.
     */
    @Column(name = "profile_picture")
    private URL profilePicture;

    /**
     * Birthday of the user.
     */
    @Column(name = "birthday")
    private Date birthday;

    public User() {
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
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

    public void setProfilePicture(String profilePicture) {
        try {
            this.profilePicture = new URL(profilePicture);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
