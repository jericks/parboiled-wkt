package org.cugos.parboiledwkt;

/**
 * A Surface is the abstract base class for Polygonal Geometries
 * @author Jared Erickson
 */
public abstract class Surface extends Geometry {

    /**
     * Create a new Surface
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public Surface(Dimension dimension, String srid) {
        super(dimension, srid);
    }

}
