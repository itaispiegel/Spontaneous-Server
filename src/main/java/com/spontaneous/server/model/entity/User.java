package com.spontaneous.server.model.entity;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

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
    @Expose
    private String facebookUserId;

    /**
     * Facebook token of the user.
     * The field is ignored since the data is private, and should be kept secured.
     */
    @Column(name = "facebook_token")
    @Expose
    private String facebookToken;

    /**
     * The name of the user.
     */
    @Column(name = "name")
    @Expose
    private String name;

    /**
     * The email of the user.
     * Each user has a unique email.
     */
    @Column(name = "email", unique = true)
    @Expose
    private String email;

    /**
     * Profile picture URL of the user.
     */
    @Column(name = "profile_picture")
    @Expose
    private String profilePicture;

    /**
     * Birthday of the user.
     */
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "birthday")
    @Expose
    private DateTime birthday;

    /**
     * Phone number of the user.
     */
    @Column(name = "phone_number")
    @Expose
    private String phoneNumber;

    /**
     * Is the user a male or a female.
     */
    @Column(name = "gender")
    @Expose
    private Gender gender;

    @Column(name = "gcm")
    @Expose
    private String gcmToken;

    /**
     * Create an empty user object.
     */
    public User() {
    }

    private User(Builder builder) {
        this.facebookUserId = builder.facebookUserId;
        this.facebookToken = builder.facebookToken;
        this.name = builder.name;
        this.email = builder.email;
        this.profilePicture = builder.profilePicture;
        this.birthday = builder.birthday;
        this.phoneNumber = builder.phoneNumber;
        this.gender = builder.gender;
        this.gcmToken = builder.gcmToken;
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
     * If there is no birthday, then set the birthday's value to the current datetime.
     */
    public void setBirthday(String birthday, String pattern) {
        if (birthday == null) {
            this.birthday = DateTime.now();
        } else {
            this.birthday = DateTimeFormat.forPattern(pattern)
                    .withZone(DateTimeZone.UTC)
                    .parseDateTime(birthday);
        }
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
    public void setGender(String gender) {
        this.gender = Gender.parse(gender);
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    /**
     * @return The age of the user in years.
     */
    public int getAge() {
        DateTime now = DateTime.now();
        return Years.yearsBetween(birthday, now).getYears();
    }

    /**
     * Create a public user profile.
     *
     * @return {@link UserProfileRO} of the user.
     */
    public UserProfileRO createPublicProfile() {
        return new UserProfileRO(name, email, profilePicture, getAge());
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
        Male, Female, Unspecified;

        /**
         * Uppercase the first letter so the string will match the enum pattern.
         *
         * @param gender Given gender to parse.
         * @return Gender enum based on the given String value.
         */
        public static Gender parse(String gender) {
            if (gender == null || gender.length() == 0) {
                return Gender.Unspecified;
            }

            return Gender.valueOf(gender.substring(0, 1).toUpperCase() + gender.substring(1));
        }
    }

    /**
     * {@code User} builder static inner class.
     */
    public static final class Builder {
        private String facebookUserId;
        private String facebookToken;
        private String name;
        private String email;
        private String profilePicture;
        private DateTime birthday;
        private String phoneNumber;
        private Gender gender;
        private String gcmToken;

        public Builder() {
        }

        /**
         * Sets the {@code facebookUserId} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code facebookUserId} to set
         * @return a reference to this Builder
         */
        public Builder facebookUserId(String val) {
            facebookUserId = val;
            return this;
        }

        /**
         * Sets the {@code facebookToken} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code facebookToken} to set
         * @return a reference to this Builder
         */
        public Builder facebookToken(String val) {
            facebookToken = val;
            return this;
        }

        /**
         * Sets the {@code name} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code name} to set
         * @return a reference to this Builder
         */
        public Builder name(String val) {
            name = val;
            return this;
        }

        /**
         * Sets the {@code email} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code email} to set
         * @return a reference to this Builder
         */
        public Builder email(String val) {
            email = val;
            return this;
        }

        /**
         * Sets the {@code profilePicture} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code profilePicture} to set
         * @return a reference to this Builder
         */
        public Builder profilePicture(String val) {
            profilePicture = val;
            return this;
        }

        /**
         * Sets the {@code birthday} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code birthday} to set
         * @return a reference to this Builder
         */
        public Builder birthday(DateTime val) {
            birthday = val;
            return this;
        }

        /**
         * Sets the {@code phoneNumber} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code phoneNumber} to set
         * @return a reference to this Builder
         */
        public Builder phoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        /**
         * Sets the {@code gender} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code gender} to set
         * @return a reference to this Builder
         */
        public Builder gender(Gender val) {
            gender = val;
            return this;
        }

        /**
         * Sets the {@code gcmToken} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code gcmToken} to set
         * @return a reference to this Builder
         */
        public Builder gcmToken(String val) {
            gcmToken = val;
            return this;
        }

        /**
         * Returns a {@code User} built from the parameters previously set.
         *
         * @return a {@code User} built with parameters of this {@code User.Builder}
         */
        public User build() {
            return new User(this);
        }
    }
}
