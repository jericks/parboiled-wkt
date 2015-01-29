package org.cugos.parboiledwkt;

import java.util.Collections;
import java.util.List;

/**
 * A PolyHedralSurface is a Surface made up of connected Polygons
 * @author Jared Erickson
 */
public class PolyHedralSurface extends Surface {

    /**
     * A List of Polygons
     */
    private final List<Polygon> polygons;

    /**
     * Create a new PolyHedralSurface
     * @param polygons The List of Polygons
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public PolyHedralSurface(List<Polygon> polygons, Dimension dimension, String srid) {
        super(dimension, srid);
        this.polygons = Collections.unmodifiableList(polygons);
    }

    /**
     * Get the List of Polygons
     * @return The List of Polygons
     */
    public List<Polygon> getPolygons() {
        return polygons;
    }

    @Override
    public boolean isEmpty() {
        return polygons.isEmpty();
    }

    @Override
    public int getNumberOfCoordinates() {
        int numberOfCoordinates = 0;
        for(Polygon polygon : this.polygons) {
            numberOfCoordinates += polygon.getNumberOfCoordinates();
        }
        return numberOfCoordinates;
    }
}
