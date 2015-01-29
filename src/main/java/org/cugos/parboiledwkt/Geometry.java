package org.cugos.parboiledwkt;

/**
 * The Abstract base class for all Geometries
 * @author Jared Erickson
 */
public abstract class Geometry {

    /**
     * The SRID
     */
    protected final String srid;

    /**
     * The Dimension
     */
    protected final Dimension dimension;

    /**
     * Create a new Geometry with Dimension and SRID
     * @param dimension The Dimension
     * @param srid The SRID
     */
    protected Geometry(Dimension dimension, String srid) {
        this.dimension = dimension;
        this.srid = srid;
    }

    /**
     * Is this Geometry empty?
     * @return Whether the Geometry is empty
     */
    public abstract boolean isEmpty();

    /**
     * Get the number of coordinates
     * @return The number of coordinates
     */
    public abstract int getNumberOfCoordinates();

    /**
     * Get the SRID which often is null
     * @return The SRID
     */
    public String getSrid() {
        return srid;
    }

    /**
     * Get the Dimension
     * @return The Dimension
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Write the Geometry to WKT
     * @return The WKT of the Geometry
     */
    @Override
    public String toString() {
        return new WKTWriter().write(this);
    }
}
