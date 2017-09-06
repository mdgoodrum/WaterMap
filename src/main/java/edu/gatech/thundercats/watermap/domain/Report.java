package edu.gatech.thundercats.watermap.domain;

/**
 * Created by Matthieu Gay-Bellile on 11/3/16.
 */
public abstract class Report {

    private String id;
    private User owner;
    private String title;
    private double[] location;
    private String description;

    /**
     * Creates report
     * @param  title       [title]
     * @param  latitude    [latitude]
     * @param  longitude   [longitude]
     * @param  description [description of the water]
     */
    public Report(
            String title,
            double latitude,
            double longitude,
            String description) {
        this.title = title;
        this.location = new double[] {longitude, latitude};
        this.description = description;
    }

    /**
     * returns id
     * @return [string representation of id]
     */
    public String getId() {
        return id;
    }

    /**
     * gets owner of report
     * @return [user who submited]
     */
    public User getOwner() {
        return owner;
    }

    /**
     * returns title of report
     * @return [string with title]
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title of report
     * @param title [title of report]
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns latitude position of report
     * @return [latitude value]
     */
    public double getLatitude() {
        return location[1];
    }

    /**
     * sets latitude value for the report
     * @param latitude [latitude value to be set]
     */
    public void setLatitude(double latitude) {
        this.location[1] = latitude;
    }

    /**
     * returns longitude of the reports location
     * @return [longitude value]
     */
    public double getLongitude() {
        return location[0];
    }

    /**
     * sets longitude value for the report
     * @param longitude [longitude value to be set]
     */
    public void setLongitude(double longitude) {
        this.location[0] = longitude;
    }

    /**
     * returns description of the water report
     * @return [description]
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description of the water report
     * @param description [description to be set]
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title + ", " + location[1] + "º " + location[0] 
            + "º - " + owner.getUsername();
    }
}
