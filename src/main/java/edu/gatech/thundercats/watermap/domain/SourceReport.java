package edu.gatech.thundercats.watermap.domain;

/**
 * Created by lavorgia on 10/6/16.
 */
public class SourceReport extends Report {
    /**
     * Enum for the condition of the water source.
     */
    public enum Condition {
        /**
         * Waste water source.
         */
        WASTE("Waste"),
        /**
         * Treatable clear water source.
         */
        TREATABLE_CLEAR("Treatable, Clear"),
        /**
         * Treatable muddy water source.
         */
        TREATABLE_MUDDY("Treatable, Muddy"),
        /**
         * Potable water source.
         */
        POTABLE("Potable");
        /**
         * Name of water source.
         */
        private final String name;

        /**
         * Gives condition the name of water source.
         *
         * @param nameOfCondition - Name of the type of water source.
         */
        Condition(final String nameOfCondition) {
            name = nameOfCondition;
        }

        /**
         * @return String name.
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enum for the type of water source.
     */
    public enum Type {
        /**
         * Bottled water.
         */
        BOTTLED("Bottled"),
        /**
         * Well water.
         */
        WELL("Well"),
        /**
         * Stream water.
         */
        STREAM("Stream"),
        /**
         * Lake water.
         */
        LAKE("Lake"),
        /**
         * Spring water.
         */
        SPRING("Spring"),
        /**
         * Other source of water not listed.
         */
        OTHER("Other");
        /**
         * Type of water source.
         */
        private final String name;

        /**
         * Gives type the name of water source.
         *
         * @param nameOfType - Name of the type of water source.
         */
        Type(final String nameOfType) {
            name = nameOfType;
        }

        /**
         * @return String name.
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Creates a condition.
     */
    private Condition condition;
    /**
     * Creates a type.
     */
    private Type type;

    /**
     * Creates the source report.
     *
     * @param title             Title of report.
     * @param latitude          Latitute of report location.
     * @param longitude         Longitude of report location.
     * @param conditionOfSource Condition of reported water.
     * @param typeofSource      Type of reported water.
     * @param description       Description of source.
     */
    public SourceReport(
            final String title,
            final double latitude,
            final double longitude,
            final Condition conditionOfSource,
            final Type typeofSource,
            final String description) {
        super(title, latitude, longitude, description);

        condition = conditionOfSource;
        type = typeofSource;
    }

    /**
     * @return Condition of water source.
     */
    public final Condition getCondition() {
        return condition;
    }

    /**
     * @param conditionOfSource Sets condition of water source.
     */
    public final void setCondition(final Condition conditionOfSource) {
        condition = conditionOfSource;
    }

    /**
     * @return Type of water source
     */
    public final Type getType() {
        return type;
    }

    /**
     * @param typeOfSource Sets type of water source.
     */
    public final void setType(final Type typeOfSource) {
        type = typeOfSource;
    }
}
