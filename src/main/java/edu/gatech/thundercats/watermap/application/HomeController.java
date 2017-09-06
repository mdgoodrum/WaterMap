package edu.gatech.thundercats.watermap.application;

// Annotations

import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.services.UserService;
import edu.gatech.thundercats.watermap.util.TaskHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

// Injected services
// Initializable

/**
 * Created by Matthieu Gay-Bellile on 9/19/16
 */
public class HomeController implements Initializable {

    @Inject
    private Router router;
    @Inject
    private Stage stage;
    @Inject
    private UserService userService;

    @FXML
    private TextField editNameField;
    @FXML
    private TextField editEmailField;
    @FXML
    private TextField editAddressField;
    @FXML
    private Text emailText;
    @FXML
    private Text addressText;
    @FXML
    private Text nameText;
    @FXML
    private Hyperlink saveName;
    @FXML
    private Hyperlink saveEmail;
    @FXML
    private Hyperlink saveAddress;
    @FXML
    private ImageView profilePicture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillFields();
    }

    /**
     * Completes the fields with the users information
     */
    private void fillFields() {
        TaskHelper.run(userService.getLoggedInUser(),
            task -> {
                User u = task.getValue();
                emailText.setText(u.getEmail());

                if (u.getAddress() != null) {
                    addressText.setText(u.getAddress());
                }

                if (u.getName() != null) {
                    nameText.setText(u.getName());
                }

                if (u.getProfilePicturePath() != null) {
                    try {
                        File file = new File(u.getProfilePicturePath());
                        profilePicture.setImage(new Image(
                            file.toURI().toURL().toExternalForm()));
                    } catch (MalformedURLException m) {
                        makeAlert(Alert.AlertType.WARNING,
                                "Picture Load Error",
                                "Your file could not be loaded properly. "
                                + "If you have changed where it was stored"
                                + "on your computer, please reset it.");
                    }
                }
            });
    }

    /**
     * Loads profile picture for user
     */
    @FXML
    private void setProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        try {
            profilePicture.setImage(
                new Image(file.toURI().toURL().toExternalForm()));
            updateUserProperty("profilePicturePath", file.getAbsolutePath());
        } catch (MalformedURLException m) {
            makeAlert(Alert.AlertType.WARNING,
                    "Picture Load Error",
                    "Your file could not be loaded "
                    + "properly. Please try again.");
        }
    }

    /**
     * Routes user back to login screen when they choose to logout
     */
    @FXML
    private void submitReportButtonClicked() {
        router.gotoRoute("submitreport");
    }

    /**
     * Opens map when button bound to this method is clicked
     */
    @FXML
    private void viewMap() {
        router.gotoRoute("map");
    }

    /**
     * Routes user back to login screen when they choose to logout
     */
    @FXML
    private void logoutButtonClicked() {
        TaskHelper.run(userService.logoutUser(),
            task -> {
                if (task.getValue()) {
                    router.gotoRoute("login");
                } else {
                    makeAlert(Alert.AlertType.WARNING,
                                "Loggout Error",
                                "There has been an error logging "
                                + "out, please try again.");
                }
            });
    }

    /**
     * Shows field for editing email and hides email text
     */
    @FXML
    private void showEmailField() {
        emailText.setVisible(false);
        editEmailField.setVisible(true);
        saveEmail.setVisible(true);
    }

    /**
     * Shows field for editing address and hides address text
     */
    @FXML
    private void showAddressField() {
        addressText.setVisible(false);
        editAddressField.setVisible(true);
        saveAddress.setVisible(true);
    }

    /**
     * Shows field for editing name and hides name text
     */
    @FXML
    private void showNameField() {
        nameText.setVisible(false);
        editNameField.setVisible(true);
        saveName.setVisible(true);
    }

    /**
     * Edits the user object's email, changes it appropriately in the UI
     */
    @FXML
    private void editEmail() {
        updateUserProperty("email", emailText, editEmailField, saveEmail);
    }

    /**
     * Edits the user object's address, changes it appropriately in the UI
     */
    @FXML
    private void editAddress() {
        updateUserProperty("address", 
            addressText, editAddressField, saveAddress);
    }

    /**
     * Edits the user object's name, changes it appropriately in the UI
     */
    @FXML
    private void editName() {
        updateUserProperty("name", nameText, editNameField, saveName);
    }

    /**
     * [updateUserProperty description]
     * @param propertyName           [Name of what is being changed]
     * @param propertyDisplayElement [What actually gets changed]
     * @param propertyEditField      [Field where you can edit the info]
     * @param propertySaveLink       [Where new info gets saved]
     */
    private void updateUserProperty(String propertyName,
                    Text propertyDisplayElement,
            TextField propertyEditField, Hyperlink propertySaveLink) {
        updateUserProperty(propertyName, propertyEditField.getText(), () -> {
            propertyEditField.setVisible(false);
            propertyDisplayElement.setVisible(true);
            propertySaveLink.setVisible(false);
        });
    }

    /**
     * [Private helper method for updating]
     * @param key   [Key for hashmap]
     * @param value [Value for hashmap]
     */
    private void updateUserProperty(String key, String value) {
        updateUserProperty(key, value, null);
    }

    /**
     * [Private helper method for updating]
     * @param key       [key for hashmao]
     * @param value     [Value for hashmap]
     * @param onSuccess [Confirms succesfull change]
     */
    private void updateUserProperty(String key, String value,
            Runnable onSuccess) {
        Map<String, String> changed = new HashMap<>(1, 1);
        changed.put(key, value);

        TaskHelper.run(userService.updateLoggedInUser(changed),
            task -> {
                if (task.getValue()) {
                    fillFields();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    makeAlert(Alert.AlertType.WARNING,
                                "Update Error",
                                "An error occured when trying to update "
                                + "your info, please try again.");
                }
            });
    }

    /**
     * [Shows dialog box if error when uploading a profile picture]
     * @param type    [Type of error]
     * @param title   [Title for error]
     * @param message [Message to be displayed]
     */
    private void makeAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
