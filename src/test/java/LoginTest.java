import edu.gatech.thundercats.watermap.Main;

import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.services.UserService;
import javax.inject.Inject;

import edu.gatech.thundercats.watermap.views.LoginView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.loadui.testfx.GuiTest.find;

import java.util.concurrent.TimeoutException;


/**
 * Created by Thomas Spader on 11/14/16.
 */

public class LoginTest extends ApplicationTest {
    @Inject
    private Router router;
    @Inject
    private UserService userService;

    TextField identifierField;
    TextField passwordField;
    Button loginButton;
    Button cancelButton;
    Hyperlink registrationLink;


    // load the GUI from its fxml file
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/edu/gatech/thundercats/watermap/views/Login.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    // retrieve elements from gui
    @Before
    public void setUp() {
        identifierField = find("#identifierField");
        passwordField = find("#passwordField");
        loginButton = find("#loginButton");
        cancelButton = find("#cancelButton");
        registrationLink = find("#registrationLink");

    }

    // close the window
    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    @Test
    public void testExist() {
        final String errMsg = "sadface becos no thing :(";
        assertNotNull(errMsg, identifierField);
        assertNotNull(errMsg, passwordField);
        assertNotNull(errMsg, loginButton);
        assertNotNull(errMsg, cancelButton);
        assertNotNull(errMsg, registrationLink);
    }

    @Test
    public void testLoginBadCredentials() {
        clickOn(identifierField).write("blarghblarghblargh");
        clickOn(passwordField).write(":^)");
        clickOn(loginButton);
        assertEquals(router.getCurrentRoute().getTitle(), "login");
    }

    @Test
    public void testLoginValidCredentials() {
        clickOn(identifierField).write("thomas");
        clickOn(passwordField).write("test");
        clickOn(loginButton);
        assertEquals(router.getCurrentRoute().getTitle(), "home");

    }

    @Test
    public void testClickRegister() {
        clickOn(registrationLink);
        assertEquals(router.getCurrentRoute().getTitle(), "registration");
    }

    @Test
    public void testEnterInfoCancel() {
        clickOn(loginButton);
        assertEquals(router.getCurrentRoute().getTitle(), "login");
    }



}
