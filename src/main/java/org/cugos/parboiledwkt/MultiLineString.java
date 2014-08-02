package org.cugos.parboiledwkt;

import java.util.List;

/**
 * A MultiLineString is a GeometryCollection made up of only LineStrings
 * @author Jared Erickson
 */
public class MultiLineString extends AbstractGeometryCollection<LineString> {

    /**
     * Create a new MultiLineString
     * @param lineStrings The List of LineStrings
     * @param dimension The Dimension
     */
    public MultiLineString(List<LineString> lineStrings, Dimension dimension) {
        this(lineStrings, dimension, null);
    }

    /**
     * Create a new MultiLineString
     * @param lineStrings The List of LineStrings
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public MultiLineString(List<LineString> lineStrings, Dimension dimension, String srid) {
        super(lineStrings, dimension, srid);
    }

    /**
     * Get the List of LineStrings
     * @return The List of LineStrings
     */
    public List<LineString> getLineStrings() {
        return this.geometries;
    }

}
