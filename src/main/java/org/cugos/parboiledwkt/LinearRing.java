package org.cugos.parboiledwkt;

import java.util.List;

/**
 * A LinearRing is a LineString that is closed
 * @author Jared Erickson
 */
public class LinearRing extends LineString {

    /**
     * Create a new LinearRing
     * @param coordinates The List of Coordinates.
     * @param dimension The Dimension
     */
    public LinearRing(List<Coordinate> coordinates, Dimension dimension) {
        this(coordinates, dimension, null);
    }

    /**
     * Create a new LinearRing
     * @param coordinates The List of Coordinates.
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public LinearRing(List<Coordinate> coordinates, Dimension dimension, String srid) {
        super(coordinates, dimension, srid);
    }

    @Override
    public int getNumberOfCoordinates() {
        return this.getCoordinates().size();
    }
}
