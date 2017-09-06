package edu.gatech.thundercats.watermap;

import com.airhacks.afterburner.injection.Injector;
import edu.gatech.thundercats.watermap.network.APIServerConnection;
import edu.gatech.thundercats.watermap.services.RemoteReportService;
import edu.gatech.thundercats.watermap.services.RemoteUserService;
import edu.gatech.thundercats.watermap.services.Router;
import edu.gatech.thundercats.watermap.util.Route;
import edu.gatech.thundercats.watermap.views.HomeView;
import edu.gatech.thundercats.watermap.views.LoginView;
import edu.gatech.thundercats.watermap.views.MapView;
import edu.gatech.thundercats.watermap.views.PurityReportsView;
import edu.gatech.thundercats.watermap.views.RegistrationView;
import edu.gatech.thundercats.watermap.views.SourceReportsView;
import edu.gatech.thundercats.watermap.views.SubmitPurityReportView;
import edu.gatech.thundercats.watermap.views.SubmitSourceReportView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dijon on 9/21/2016.
 */
public class Main extends Application implements Router {
    /**
     * Main stage.
     */
    private Stage stage;
    /**
     * Map of routes.
     */
    private Map<String, Route> routes;

    private Route currentRoute;

    /**
     * Starts the application.
     *
     * @param stage Main stage
     */
    public final void start(final Stage stage) {
        // HACK: Silence afterburner.fx warning log messages
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(final int b) {
            }
        }));

        this.stage = stage;

        Map<Object, Object> context = new HashMap<>();
        context.put("router", this);

        APIServerConnection api = new APIServerConnection(
                "https://thundercats-water-map.herokuapp.com/api");
//        APIServerConnection api =
//        new APIServerConnection("http://localhost:5000/api");

        context.put("userService", new RemoteUserService(api));
        context.put("reportService", new RemoteReportService(api));

        context.put("stage", stage);
        Injector.setConfigurationSource(context::get);

        routes = new HashMap<>();
        routes.put("login", new Route(LoginView.class, "Log In"));
        routes.put("viewsourcereports", new Route(
                SourceReportsView.class, "Source Reports"));
        routes.put("viewpurityreports", new Route(
                PurityReportsView.class, "View Reports"));
        routes.put("home", new Route(HomeView.class, "Home"));
        routes.put("registration", new Route(
                RegistrationView.class, "Register"));
        routes.put("submitsourcereport", new Route(
                SubmitSourceReportView.class, "Submit Source Report"));
        routes.put("submitpurityreport", new Route(
                SubmitPurityReportView.class, "Submit Purity Report"));
        routes.put("map", new Route(MapView.class, "Water Map"));

        gotoRoute("login");
    }

    /**
     * Routes a user to a given screen.
     *
     * @param sceneName name of route in routes hash map
     */
    public final void gotoRoute(final String sceneName) {
        Route route = routes.get(sceneName);
        stage.setTitle(route.getTitle());
        stage.setScene(route.createScene());
        stage.show();
        currentRoute = route;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    /**
     * Exits the application.
     */
    public final void exit() {
        Platform.exit();
    }

    /**
     * launches the application.
     * @param args provided arguments
     */
    public static final void main(final String[] args) {
        launch(args);
    }

}
