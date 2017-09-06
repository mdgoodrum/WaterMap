import edu.gatech.thundercats.watermap.Main;
import edu.gatech.thundercats.watermap.domain.PurityReport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.loadui.testfx.GuiTest.find;

/**
 * Created by Michael Goodrum
 */
public class SubmitPurityReportControllerTest extends ApplicationTest {
    TextField titleField;
    TextField latField;
    TextField longField;
    ComboBox<PurityReport.Condition> conditionComboBox;
    TextField contaminantPPMField;
    TextField virusPPMField;
    TextArea descriptionField;
    Parent mainNode;


    @Override
    public void start(Stage stage) throws Exception {
        mainNode = FXMLLoader.load(Main.class.getResource("/edu/gatech/thundercats/watermap/views/SubmitPurityReport.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp() {
        titleField = find("#titleField");
        latField = find("#latField");
        longField= find("#longField");
        conditionComboBox = find("#conditionComboBox");
        contaminantPPMField = find("#contaminantPPMField");
        virusPPMField = find("#virusPPMField");
        descriptionField = find("#descriptionField");
    }

    @Test
    public void login() {
        clickOn(titleField).write("Georgia Tech Water");
        clickOn(latField).write("33.7756");
        clickOn(longField).write("-84.3963");
        clickOn(conditionComboBox).clickOn("Safe");
        clickOn(contaminantPPMField).write("8");
        clickOn(virusPPMField).write("6");
        clickOn(descriptionField).write("Water at our school.");
        assertEquals("Georgia Tech Water", titleField.getText());
        assertEquals("33.7756", latField.getText());
        assertEquals("-84.3963", longField.getText());
        assertEquals("8", contaminantPPMField.getText());
        assertEquals("6", virusPPMField.getText());
        assertEquals("Water at our school.", descriptionField.getText());

        WaitForAsyncUtils.waitForFxEvents();
    }
}