package edu.gatech.thundercats.watermap.application;

import edu.gatech.thundercats.watermap.domain.SourceReport;
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
public class SubmitSourceReportController implements Initializable {
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
    private ComboBox<SourceReport.Condition> conditionComboBox;
    @FXML
    private ComboBox<SourceReport.Type> typeComboBox;
    @FXML
    private TextArea descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conditionComboBox.setItems(
            FXCollections.observableArrayList(
                SourceReport.Condition.values()));
        typeComboBox.setItems(FXCollections.observableArrayList(
            SourceReport.Type.values()));
    }

    /**
     * submits source report when bound button is clicked
     */
    @FXML
    private void submitButtonClicked() {
        if (!allFieldsNotNull()) {
            makeAlert(Alert.AlertType.WARNING, "Invalid Source Report", 
                "Please fill in all boxes to submit a "
                + "report. Description is optional.");
        } else {
            try {
                String title = titleField.getText();
                double latitude = Double.parseDouble(latField.getText());
                double longitude = Double.parseDouble(longField.getText());
                String description = descriptionField.getText() 
                    == null ? "" : descriptionField.getText();
                SourceReport.Condition condition = conditionComboBox.getValue();
                SourceReport.Type type = typeComboBox.getValue();

                TaskHelper.run(reportService.addReport(new SourceReport(
                    title, latitude, longitude, condition, type, description)),
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
                        "Latitude and longitude must be numbers");
            }
        }
    }

    /**
     * Creates allert when there is an error submiting report
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
     * Makes sure all fields are filled in before submiting report
     * @return [true if all are filled]
     */
    private boolean allFieldsNotNull() {
        if (conditionComboBox.getValue() == null 
            || typeComboBox.getValue() == null 
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
