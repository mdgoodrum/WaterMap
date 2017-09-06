package edu.gatech.thundercats.watermap.util;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;

/**
 * Created by Matthieu Gay-Bellile on 9/25/16.
 */
public class Route {
    /**
     * Creates an instance of the FXMLView class.
     */
    private final Class<? extends FXMLView> viewClass;
    /**
     * Title for window.
     */
    private final String windowTitle;

    /**
     * Assigns the view and title with class constructor.
     *
     * @param viewClass   Class of current view
     * @param windowTitle Title for window
     */
    public Route(final Class<? extends FXMLView> viewClass,
                 final String windowTitle) {
        this.viewClass = viewClass;
        this.windowTitle = windowTitle;
    }

    /**
     * Creates a new scene based on the
     * boilerplate class corresponding to FXML file.
     *
     * @return the new scene
     */
    public final Scene createScene() {
        try {
            return new Scene(viewClass.newInstance().getView());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @return String title of window
     */
    public final String getTitle() {
        return windowTitle;
    }
}
