package edu.gatech.thundercats.watermap.application;

import edu.gatech.thundercats.watermap.domain.PurityReport;
import edu.gatech.thundercats.watermap.services.ReportService;
import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.util.TaskHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lavorgia on 10/10/16.
 */
public class SubmitPurityReportController implements Initializable {
    @Inject
    private Router router;
    @Inject
    private ReportService reportService;

    @FXML
    private TextField titleField;
    @FXML
    private TextField latField;
    @FXML
    private TextField longField;
    @FXML
    private ComboBox<PurityReport.Condition> conditionComboBox;
    @FXML
    private TextField contaminantPPMField;
    @FXML
    private TextField virusPPMField;
    @FXML
    private TextArea descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conditionComboBox.setItems(FXCollections.observableArrayList(
            PurityReport.Condition.values()));
    }

    /**
     * submits purity report when bound button is clicked
     */
    @FXML
    private void submitButtonClicked() {
        if (!allFieldsNotNull()) {
            makeAlert(Alert.AlertType.WARNING, "Invalid Purity Report", 
                "Please fill in all boxes to submit a report. "
                + "Description is optional.");
        } else {
            try {
                String title = titleField.getText();
                double latitude = Double.parseDouble(latField.getText());
                double longitude = Double.parseDouble(longField.getText());
                PurityReport.Condition condition = conditionComboBox.getValue();
                double contaminantPPM = Double.parseDouble(
                    contaminantPPMField.getText());
                double virusPPM = Double.parseDouble(
                    virusPPMField.getText());
                String description = descriptionField
                    .getText() == null ? "" : descriptionField.getText();

                TaskHelper.run(reportService.addReport(
                    new PurityReport(title, latitude, longitude, 
                    condition, contaminantPPM, virusPPM, description)),
                    task -> {
                        if (task.getValue()) {
                            makeAlert(Alert.AlertType.INFORMATION,
                                    "Success!",
                                    task.getMessage());
                        } else {
                            makeAlert(Alert.AlertType.WARNING,
                                    "Error",
                                    task.getMessage());
                        }
                    });
            } catch (RuntimeException r) {
                makeAlert(Alert.AlertType.WARNING,
                        "Entry error",
                        "Latitude, longitude, contaminant ppm, "
                        + "and virus ppm must be numbers");
            }
        }
    }

    /**
     * creates allert when report isn't filled correctly
     * @param type    [Error type]
     * @param title   [Error title]
     * @param message [Message to be displayed]
     */
    private void makeAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * makes sure all fields are filled before submiting
     * @return [true if all fields are completed]
     */
    private boolean allFieldsNotNull() {
        if (conditionComboBox.getValue() == null 
            || contaminantPPMField.getText() == "" 
            || virusPPMField.getText() == "" 
            || titleField.getText() == "" 
            || latField.getText() == "" 
            || longField.getText() == "") {
            return false;
        }

        return true;
    }

    /**
     * returns to map when bound button is clicked
     */
    @FXML
    private void returnButtonClicked() {
        router.gotoRoute("map");
    }
}
