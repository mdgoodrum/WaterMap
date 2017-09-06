package edu.gatech.thundercats.watermap.application;

import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.services.UserService;
import edu.gatech.thundercats.watermap.util.TaskHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Thomas Spader on 9/26/16.
 */
public class RegistrationController implements Initializable {

    @Inject
    private Router router;
    @Inject
    private UserService userService;

    @Inject
    private Stage stage;

    @FXML
    private Label errorMessage;
    @FXML
    private Label successMessage;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<User.Role> roleComboBox;
    @FXML
    private Button registerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.setItems(
            FXCollections.observableArrayList(User.Role.values()));
    }

    /**
     * authenticates that user's registration is valid
     */
    @FXML
    private void handleRegisterUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        User.Role role = roleComboBox.getValue();
        if (!username.isEmpty()) {
            if (!password.isEmpty()) {
                if (!email.isEmpty()) {
                    if (role != null) {
                        hideMessages();
                        registerButton.setDisable(true);

                        TaskHelper.run(userService.registerUser(
                            new User(username, password, email, role)),
                            task -> {
                                if (task.getValue()) {
                                    usernameField.clear();
                                    passwordField.clear();
                                    emailField.clear();
                                    roleComboBox.getSelectionModel()
                                        .clearSelection();
                                    showSuccessMessage(task.getMessage());
                                    try {
                                        Thread.sleep(900);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    router.gotoRoute("login");
                                } else {
                                    showErrorMessage(task.getMessage());
                                }
                                registerButton.setDisable(false);
                            },
                            task -> {
                                showErrorMessage(task.getMessage());
                                registerButton.setDisable(false);
                            },
                            task -> registerButton.setDisable(false)
                        );
                    } else {
                        showErrorMessage("Role cannot be empty.");
                    }
                } else {
                    showErrorMessage("Email cannot be empty.");
                }
            } else {
                showErrorMessage("Password cannot be empty.");
            }
        } else {
            showErrorMessage("Username cannot be empty.");
        }
    }

    /**
     * returns to login when bound button is clicked
     */
    @FXML
    private void handleGoBack() {
        router.gotoRoute("login");
    }

    /**
     * @param message message to be displayed
     */
    private void showErrorMessage(String message) {
        hideSuccessMessage();
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        errorMessage.setManaged(true);
        stage.sizeToScene();
    }

    /**
     * @param message to be displayed
     */
    private void showSuccessMessage(String message) {
        hideErrorMessage();
        successMessage.setText(message);
        successMessage.setVisible(true);
        successMessage.setManaged(true);
        stage.sizeToScene();
    }

    /**
     * hides success and error messages
     */
    private void hideMessages() {
        hideErrorMessage();
        hideSuccessMessage();
        stage.sizeToScene();
    }


    /**
     * these do not resize the stage, do not call directly
     */
    private void hideErrorMessage() {
        errorMessage.setVisible(false);
        errorMessage.setManaged(false);
    }

    /**
     * removes success message
     */
    private void hideSuccessMessage() {
        successMessage.setVisible(false);
        successMessage.setManaged(false);
    }

}
