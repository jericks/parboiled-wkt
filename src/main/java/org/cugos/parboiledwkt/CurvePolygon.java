package org.cugos.parboiledwkt;

import java.util.Collections;
import java.util.List;

/**
 * A CurvePolygon is Surface made up of Curves.
 * @author Jared Erickson
 */
public class CurvePolygon extends Surface {

    /**
     * The outer Curve
     */
    private final Curve outerCurve;

    /**
     * The List of inner Curves
     */
    private final List<Curve> innerCurves;

    /**
     * Create a new CurvePolygon
     * @param outerCurve The outer Curve
     * @param innerCurves The List of inner Curves
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public CurvePolygon(Curve outerCurve, List<Curve> innerCurves, Dimension dimension, String srid) {
        super(dimension, srid);
        this.outerCurve = outerCurve;
        this.innerCurves = Collections.unmodifiableList(innerCurves);
    }

    /**
     * Create a new CurvePolygon
     * @param outerCurve The outer Curve
     * @param innerCurves The List of inner Curves
     * @param dimension The Dimension
     */
    public CurvePolygon(Curve outerCurve, List<Curve> innerCurves, Dimension dimension) {
        this(outerCurve, innerCurves, dimension, null);
    }

    /**
     * Get the outer Curve
     * @return The outer Curve
     */
    public Curve getOuterCurve() {
        return outerCurve;
    }

    /**
     * Get the List of inner Curves
     * @return The List of inner Curves
     */
    public List<Curve> getInnerCurves() {
        return innerCurves;
    }

    @Override
    public boolean isEmpty() {
        return outerCurve == null | outerCurve.isEmpty();
    }
}
