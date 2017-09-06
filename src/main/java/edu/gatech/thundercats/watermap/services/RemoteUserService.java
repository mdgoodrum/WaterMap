package edu.gatech.thundercats.watermap.services;

import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.network.APIServerConnection;
import javafx.concurrent.Task;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthieu Gay-Bellile on 10/3/16.
 */
public class RemoteUserService implements UserService {
    /**
     * API Server Connection needed for remote persistence.
     */
    private final APIServerConnection api;
    /**
     * Sets the api server connection in the class constructor.
     *
     * @param api api server connection object
     */
    public RemoteUserService(final APIServerConnection api) {
        this.api = api;
    }

    @Override
    public final Task<Boolean> registerUser(final User user) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                JSONObject response = api.post("/auth/register", user,
                        error -> updateMessage(error));

                updateMessage(response.getString("message"));
                return response.getBoolean("success");
            }
        };
    }

    @Override
    public final Task<Boolean> authenticateUser(
            final String identifier, final String password) {
        Map<String, String> credentials = new HashMap<>(2, 1);
        credentials.put("identifier", identifier);
        credentials.put("password", password);

        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {

                JSONObject response = api.post("/auth/login", credentials,
                        error -> updateMessage(error));

                boolean success = response.getBoolean("success");
                if (success) {
                    api.setAuthToken(response.getString("token"));
                } else {
                    updateMessage(response.getString("message"));
                }
                return success;
            }
        };
    }

    @Override
    public final Task<Boolean> logoutUser() {
        api.setAuthToken(null);
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                updateMessage("Successfully logged out.");
                return true;
            }
        };
    }

    @Override
    public final Task<User> getLoggedInUser() {
        return new Task<User>() {
            @Override
            protected User call() throws Exception {
                return api.getAsObject("/users/me", User.class,
                        error -> updateMessage(error));
            }
        };
    }

    @Override
    public final <T> Task<Boolean> updateLoggedInUser(final T changed) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                JSONObject response = api.put("/users/me", changed,
                        error -> updateMessage(error));
                updateMessage(response.getString("message"));
                return response.getBoolean("success");
            }
        };
    }
}
