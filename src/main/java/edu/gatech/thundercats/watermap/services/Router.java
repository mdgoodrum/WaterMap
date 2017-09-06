package edu.gatech.thundercats.watermap.services;

import edu.gatech.thundercats.watermap.util.Route;

/**
 * Created by Matthieu Gay-Bellile on 9/25/16.
 */
public interface Router {
    /**
     * @param route Screen to change to.
     */
    void gotoRoute(String route);

    Route getCurrentRoute();

    /**
     * Exits current screen.
     */
    void exit();
}
