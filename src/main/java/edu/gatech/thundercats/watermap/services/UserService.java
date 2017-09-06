package edu.gatech.thundercats.watermap.services;

import edu.gatech.thundercats.watermap.domain.User;
import javafx.concurrent.Task;

/**
 * Created by Matthieu Gay-Bellile on 9/24/16.
 */
public interface UserService {
    /**
     * @param user User to be registered
     * @return Whether process is successful
     */
    Task<Boolean> registerUser(User user);

    /**
     * @param identifier User to be authenticated
     * @param  password Password to check against
     * @return Whether process is successful
     */
    Task<Boolean> authenticateUser(String identifier, String password);

    /**
     * @return Whether process is successful
     */
    Task<Boolean> logoutUser();

    /**
     * @return User Logged in
     */
    Task<User> getLoggedInUser();

    /**
     * @param changed User being updated
     * @param <T>     Made Generic
     * @return Whether process is successful
     */
    <T> Task<Boolean> updateLoggedInUser(T changed);
}
