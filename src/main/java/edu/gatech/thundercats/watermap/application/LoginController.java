package edu.gatech.thundercats.watermap.application;

// Annotations

import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.services.UserService;
import edu.gatech.thundercats.watermap.util.TaskHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

// Injected services
// Initializable

/**
 * Created by Matthieu Gay-Bellile on 9/19/16.
 */
public class LoginController implements Initializable {

    @Inject
    private Router router;
    @Inject
    private UserService userService;

    @Inject
    private Stage stage;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField identifierField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * checks if user's credentials are valid. if so, routes them to home screen
     */
    @FXML
    private void handleLogin() {
        hideMessages();
        loginButton.setDisable(true);

        TaskHelper.run(userService.authenticateUser(identifierField.getText(),
            passwordField.getText()),
            task -> {
                if (task.getValue()) {
                    router.gotoRoute("home");
                } else {
                    loginButton.setDisable(false);
                    showErrorMessage(task.getMessage());
                }
            },
            task -> {
                System.out.println(task.getException());
            });
    }

    /**
     * brings user to registration screen when they click registration link
     */
    @FXML
    private void handleGotoRegister() {
        router.gotoRoute("registration");
    }

    /**
     * Closes window if clicked
     */
    @FXML
    private void handleCancel() {
        router.exit();
    }

    /**
     * @param message the text to be displayed
     */
    private void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        errorMessage.setManaged(true);
        stage.sizeToScene();
    }

    /**
     * hides error messages that were previously shown
     */
    private void hideMessages() {
        hideErrorMessage();
        stage.sizeToScene();
    }


    /**
     * These do not resize the stage, do not call directly
     */
    private void hideErrorMessage() {
        errorMessage.setVisible(false);
        errorMessage.setManaged(false);
    }
}
