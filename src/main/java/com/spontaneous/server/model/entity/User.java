package com.spontaneous.server.model.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    /**
     * The email of the user.
     */
    @Column(name = "email")
    private String email;

    /**
     * Profile picture URL of the user.
     */
    @Column(name = "profile_picture")
    private String profilePicture;

    /**
     * Birthday of the user.
     */
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "birthday")
    private DateTime birthday;

    /**
     * Phone number of the user.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Is the user a male or a female.
     */
    @Column(name = "gender")
    private Gender gender;

    /**
     * Create an empty user object.
     */
    public User() {
    }

    /**
     * @return facebook user id.
     */
    public String getFacebookUserId() {
        return facebookUserId;
    }

    /**
     * Sets the facebook user id.
     */
    public void setFacebookUserId(String facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    /**
     * @return facebook token of the user.
     */
    public String getFacebookToken() {
        return facebookToken;
    }

    /**
     * Sets the facebook token of the user.
     */
    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    /**
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return profile picture URL of the user.
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Sets the profile picture of the user.
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * @return birthday of the user.
     */
    public DateTime getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the user.
     */
    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * Sets the birthday of the user.
     */
    public void setBirthday(String birthday) {
        this.birthday = new DateTime(birthday);
    }

    /**
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return whether the user is a male or female.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set whether the user is a male or a female.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Set whether the user is a male or a female.
     */
    public void setGender(String gender) {
        if(gender.equalsIgnoreCase("male")) {
            this.gender = Gender.Male;
        } else if(gender.equalsIgnoreCase("female")) {
            this.gender = Gender.Female;
        } else {
            this.gender = Gender.Unspecified;
        }
    }

    /**
     * Return a string representation of the user.
     */
    @Override
    public String toString() {
        return "User{" +
                "facebookUserId='" + facebookUserId + '\'' +
                ", facebookToken='" + facebookToken + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", birthday=" + birthday +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                "} " + super.toString();
    }

    public enum Gender {
        Male, Female, Unspecified
    }
}
