package edu.gatech.thundercats.watermap.util;

/**
 * Created by lavorgia on 9/30/16.
 */

/**
 * class made to allow serial generation *from arbitrary index*
 * so that persistent data storage interfaces can properly assign new ids
 * instead of beginning at serial number 0
 */
public class SerialGenerator {
    private int index = -1;

    /**
     * @param index position in index to continue from
     */
    public SerialGenerator(int index) {
        this.index = index;
    }
    // guaranteed to return a new ID on each call

    /**
     * @return integer unique to this class
     */
    public int makeID() {
        return ++index;
    }

    public int nextId() {
        return index + 1;
    }
}
