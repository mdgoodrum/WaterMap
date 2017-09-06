package edu.gatech.thundercats.watermap.application;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import edu.gatech.thundercats.watermap.domain.SourceReport;
import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.services.ReportService;
import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.services.UserService;
import edu.gatech.thundercats.watermap.util.TaskHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Thomas Spader on 9/26/16.
 */
public class MapController implements Initializable,
    MapComponentInitializedListener {

    @Inject
    private Router router;
    @Inject
    private UserService userService;
    @Inject
    private ReportService reportService;

    @Inject
    private Stage stage;

    @FXML
    private GoogleMapView mapView;
    @FXML
    private Button createPurityReportButton;
    @FXML
    private MenuItem viewPurityReportsItem;

    private GoogleMap map;

    /**
     * Initializes the map
     * @param location  [initial map start up]
     * @param resources [pins to be placed]
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        TaskHelper.run(userService.getLoggedInUser(),
            task -> {
                User.Role loggedInUserRole = task.getValue().getRole();
                if (loggedInUserRole.ordinal() >= User.Role.WORKER.ordinal()) {
                    createPurityReportButton.setManaged(true);
                }
                if (loggedInUserRole.ordinal() >= User.Role.MANAGER.ordinal()) {
                    viewPurityReportsItem.setVisible(true);
                }
            });
    }

    /**
     * returns user to home screen
     */
    @FXML
    private void returnHomeClicked() { 
        router.gotoRoute("home"); 
    }

    /**
     * goes to source reports
     */
    @FXML
    private void viewSourceReports() { 
        router.gotoRoute("viewsourcereports"); 
    }

    /**
     * goes to purity reports
     */
    @FXML
    private void viewPurityReports() { 
        router.gotoRoute("viewpurityreports"); 
    }

    /**
     * uploads source report
     */
    @FXML
    private void createSourceReport() { 
        router.gotoRoute("submitsourcereport"); 
    }

    /**
     * uploads purity report
     */
    @FXML
    private void createPurityReport() { 
        router.gotoRoute("submitpurityreport"); 
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        //set up the center location for the map
        LatLong center = new LatLong(34, -88);

        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);

        TaskHelper.run(reportService.getSourceReports(),
            task -> {
                for (SourceReport s : task.getValue()) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLong loc = new LatLong(s.getLatitude(),
                        s.getLongitude());

                    markerOptions.position(loc)
                            .visible(Boolean.TRUE)
                            .title(s.getTitle());

                    Marker marker = new Marker(markerOptions);

                    map.addUIEventHandler(marker,
                            UIEventType.click,
                        (JSObject obj) -> {
                            InfoWindowOptions infoWindowOptions = 
                                new InfoWindowOptions();
                            infoWindowOptions.content(s.getDescription());

                            InfoWindow window = 
                                new InfoWindow(infoWindowOptions);
                            window.open(map, marker);
                        });

                    map.addMarker(marker);
                }
            },
            task -> {
                System.out.println(task.getException());
            });
    }
}
