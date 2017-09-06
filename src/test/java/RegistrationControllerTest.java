import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import org.junit.Test;


/**
 * Created by Dijon on 11/14/2016.
 */
public class RegistrationControllerTest extends TestFXBase {

    @Test
    public void RegistrationOpened() {
        clickOn("#registrationLink");
        Label label = find("#registerLabel");
        assert (label.getText().equals("Register"));
    }

    @Test
    public void AllFieldsEmptyError() {
        clickOn("#registrationLink");
        clickOn("#registerButton");
        Label error = find("#errorMessage");
        assert (error.isVisible());
    }

    @Test
    public void UserNameFieldEmptyError() {
        clickOn("#registrationLink");
        clickOn("#passwordField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#emailField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#roleComboBox");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#registerButton");
        Label error = find("#errorMessage");
        assert (error.getText().equals("Username cannot be empty."));
    }

    @Test
    public void PasswordFieldEmptyError() {
        clickOn("#registrationLink");
        clickOn("#usernameField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#emailField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#roleComboBox");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#registerButton");
        Label error = find("#errorMessage");
        assert (error.getText().equals("Password cannot be empty."));
    }

    @Test
    public void EmailFieldEmptyError() {
        clickOn("#registrationLink");
        clickOn("#usernameField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#passwordField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#roleComboBox");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#registerButton");
        Label error = find("#errorMessage");
        assert (error.getText().equals("Email cannot be empty."));
    }

    @Test
    public void roleComboBoxEmptyError() {
        clickOn("#registrationLink");
        clickOn("#usernameField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#passwordField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#emailField");
        type(KeyCode.getKeyCode("1"), 5);
        clickOn("#registerButton");
        Label error = find("#errorMessage");
        assert (error.getText().equals("Role cannot be empty."));
    }
    @Test
    public void BackReturnsToLogin() {
        clickOn("#registrationLink");
        clickOn("#backButton");
        Label label = find("#loginLabel");
        assert (label.getText().equals("WaterMap"));
    }


}