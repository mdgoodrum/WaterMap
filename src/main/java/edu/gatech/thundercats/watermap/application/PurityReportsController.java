package edu.gatech.thundercats.watermap.application;

import edu.gatech.thundercats.watermap.domain.PurityReport;
import edu.gatech.thundercats.watermap.services.ReportService;
import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.util.TaskHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Michael Goodrum on 10/28/2016.
 */
public class PurityReportsController implements Initializable {
    @FXML
    private ListView<PurityReport> reportView;
    @Inject
    private ReportService reportService;
    @Inject
    private Router router;

    /**
     * returns to the map when bound button is clicked
     */
    @FXML
    private void returnMapClicked() {
        router.gotoRoute("map");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TaskHelper.run(reportService.getPurityReports(), 
            task -> reportView.setItems(
                FXCollections.observableArrayList(task.getValue())));
    }
}
