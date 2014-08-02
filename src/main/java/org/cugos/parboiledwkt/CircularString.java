package org.cugos.parboiledwkt;

import java.util.Collections;
import java.util.List;

/**
 * A CircularString is a Curve made up of three or more Coordinates.
 * @author Jared Erickson
 */
public class CircularString extends Curve {

    /**
     * The List of Coordinates
     */
    private final List<Coordinate> coordinates;

    /**
     * Create a new CircularString
     * @param coordinates The List of Coordinates
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public CircularString(List<Coordinate> coordinates, Dimension dimension, String srid) {
        super(dimension, srid);
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    /**
     * Create a new CircularString
     * @param coordinates The List of Coordinates
     * @param dimension The Dimension
     */
    public CircularString(List<Coordinate> coordinates, Dimension dimension) {
        this(coordinates, dimension, null);
    }

    /**
     * Get the List of Coordinates
     * @return The List of Coordinates
     */
    public List<Coordinate> getCoordinates() {
        return Collections.unmodifiableList(coordinates);
    }

    @Override
    public boolean isEmpty() {
        return coordinates.isEmpty();
    }

}
