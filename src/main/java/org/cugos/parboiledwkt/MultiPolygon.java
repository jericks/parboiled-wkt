package org.cugos.parboiledwkt;

import java.util.List;

/**
 * A MultiPolygon is a GeometryCollection made up of only Polygons.
 * @author Jared Erickson
 */
public class MultiPolygon extends AbstractGeometryCollection<Polygon> {

    /**
     * Create a new MultiPolygon
     * @param polygons The List of Polygons
     * @param dimension The Dimension
     */
    public MultiPolygon(List<Polygon> polygons, Dimension dimension) {
        this(polygons, dimension, null);
    }

    /**
     * Create a new MultiPolygon
     * @param polygons The List of Polygons
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public MultiPolygon(List<Polygon> polygons, Dimension dimension, String srid) {
        super(polygons, dimension, srid);
    }

    /**
     * Get the List of Polygons
     * @return The List of Polygons
     */
    public List<Polygon> getPolygons() {
        return geometries;
    }

}
