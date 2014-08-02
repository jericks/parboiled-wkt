package org.cugos.parboiledwkt;

import java.util.List;

/**
 * A MultiPoint is a GeometryCollection made up of only Points
 * @author Jared Erickson
 */
public class MultiPoint extends AbstractGeometryCollection<Point> {

    /**
     * Create new MultiPoint
     * @param points The List of MultiPoints
     * @param dimension The Dimension
     */
    public MultiPoint(List<Point> points, Dimension dimension) {
        this(points, dimension, null);
    }

    /**
     * Create new MultiPoint
     * @param points The List of MultiPoints
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public MultiPoint(List<Point> points, Dimension dimension, String srid) {
        super(points, dimension, srid);
    }

    /**
     * Get the List of Points
     * @return The List of Points
     */
    public List<Point> getPoints() {
        return this.geometries;
    }

}
