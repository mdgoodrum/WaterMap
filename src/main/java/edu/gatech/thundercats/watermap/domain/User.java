package edu.gatech.thundercats.watermap.domain;

/**
 * Created by Matthieu Gay-Bellile on 9/19/16.
 */
public class User {
    /**
     * Enum for the roles users may have.
     */
    public enum Role {
        /**
         * Role that may view reports.
         */
        USER("User"),
        /**
         * Role that may submit reports.
         */
        WORKER("Worker"),
        /**
         * Role that may manage submitted reports.
         */
        MANAGER("Manager"),
        /**
         * Role with all privileges.
         */
        ADMIN("Admin");
        /**
         * Name of role.
         */
        private final String name;

        /**
         * @param userRole Name of user role.
         */
        Role(final String userRole) {
            name = userRole;
        }

        /**
         * @return String name.
         */
        public String toString() {
            return name;
        }
    }

    /**
     * User ID.
     */
    private String id;
    /**
     * User username.
     */
    private String username;
    /**
     * User password.
     */
    private String password;
    /**
     * User email.
     */
    private String email;
    /**
     * User role.
     */
    private Role role;
    /**
     * Name of User.
     */
    private String name;
    /**
     * User Address.
     */
    private String address;
    /**
     * User path for profile picture.
     */
    private String profilePicturePath;

    /**
     * @param username User username
     * @param password User password
     * @param email    User email
     * @param role     User role
     */
    public User(final String username, final String password,
                final String email, final Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    /**
     * @return User ID
     */
    public final String getId() {
        return id;
    }

    /**
     * @return User username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets username to given username.
     *
     * @param username User username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return User password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Sets password to given password.
     *
     * @param password User password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return User email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Sets email to given email.
     *
     * @param email User email
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return User role
     */
    public final Role getRole() {
        return role;
    }

    /**
     * @return Name of User
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets name of user to given name of user.
     *
     * @param name Name of user
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * @return User address
     */
    public final String getAddress() {
        return address;
    }

    /**
     * Sets user address to given user address.
     *
     * @param address User address
     */
    public final void setAddress(final String address) {
        this.address = address;
    }

    /**
     * @return User profile picture picture path
     */
    public final String getProfilePicturePath() {
        return profilePicturePath;
    }

    /**
     * Sets user profile picture path to given path.
     *
     * @param path User profile picture path
     */
    public final void setProfilePicturePath(final String path) {
        this.profilePicturePath = path;
    }
}
