package org.cugos.parboiledwkt;

import java.util.Collections;
import java.util.List;

/**
 * An abstract base class for GeometryCollections
 * @param <T> The type of Geometry
 */
public abstract class AbstractGeometryCollection<T extends Geometry> extends Geometry {

    /**
     * The List of Geometries
     */
    protected final List<T> geometries;

    /**
     * Create a new AbstractGeometryCollection
     * @param geometries A List of Geometries
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public AbstractGeometryCollection(List<T> geometries, Dimension dimension, String srid) {
        super(dimension, srid);
        this.geometries = Collections.unmodifiableList(geometries);
    }

    @Override
    public boolean isEmpty() {
        return this.geometries.isEmpty();
    }

    @Override
    public String toString() {
        return new WKTWriter().write(this);
    }
}
