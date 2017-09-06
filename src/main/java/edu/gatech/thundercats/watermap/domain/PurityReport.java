package edu.gatech.thundercats.watermap.domain;

/**
 * Created by lavorgia on 10/6/16.
 */
public class PurityReport extends Report {
    
    public enum Condition {
        SAFE("Safe"),
        TREATABLE("Treatable"),
        UNSAFE("Unsafe");

        private final String name;
        
        /**
         * condition of water
         * @param name [description of condition]
         */
        Condition(String name) {
            this.name = name;
        }
        
        /**
         * toString method for water condition
         * @return [description of condition]
         */
        public String toString() {
            return name;
        }
    }
    
    private Condition condition;
    private double contaminantPPM;
    private double virusPPM;


    /**
     * [PurityReport]
     * @param  title          [title]
     * @param  latitude       [latitude]
     * @param  longitude      [longitude]
     * @param  condition      [water condition]
     * @param  contaminantPPM [water ppm contamination]
     * @param  virusPPM       [water virus ppm]
     * @param  description    [description of the water]
     */
    public PurityReport(
            String title,
            double latitude,
            double longitude,
            Condition condition,
            double contaminantPPM,
            double virusPPM,
            String description) {
        super(title, latitude, longitude, description);

        this.condition = condition;
        this.contaminantPPM = contaminantPPM;
        this.virusPPM = virusPPM;
    }

    /**
     * [returns description of the water]
     * @return [condition]
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * [sets condition for location]
     * @param condition [condition to be set]
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * [gets contaminant ppm value]
     * @return [ppm value]
     */
    public double getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * [sets contaminant ppm value]
     * @param contaminantPPM [ppm value]
     */
    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * [gets virus ppm value]
     * @return [ppm value]
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * [sets virus ppm value]
     * @param virusPPM [ppm value]
     */
    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }
}
