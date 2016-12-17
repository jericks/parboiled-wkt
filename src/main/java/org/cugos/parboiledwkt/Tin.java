package org.cugos.parboiledwkt;

import java.util.Collections;
import java.util.List;

/**
 * A Tin is a Surface made up of Triangles
 * @author Jared Erickson
 */
public class Tin extends Surface {

    /**
     * The List of Triangles
     */
    private final List<Triangle> triangles;

    /**
     * Create a new Tin
     * @param triangles The List of Triangles
     * @param dimension The Dimension
     */
    public Tin(List<Triangle> triangles, Dimension dimension) {
        this(triangles, dimension, null);
    }

    /**
     * Create a new Tin
     * @param triangles The List of Triangles
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public Tin(List<Triangle> triangles, Dimension dimension, String srid) {
        super(dimension, srid);
        this.triangles = Collections.unmodifiableList(triangles);
    }

    /**
     * Get the List of Triangles
     * @return The List of Triangles
     */
    public List<Triangle> getTriangles() {
        return triangles;
    }

    @Override
    public boolean isEmpty() {
        return triangles.isEmpty();
    }

    @Override
    public int getNumberOfCoordinates() {
        int numberOfCoordinates = 0;
        for(Triangle triangle : this.triangles) {
            numberOfCoordinates += triangle.getNumberOfCoordinates();
        }
        return numberOfCoordinates;
    }
}
