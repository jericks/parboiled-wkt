package org.cugos.parboiledwkt;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a Geometry to a WKT String.
 * @author Jared Erickson
 */
public class WKTWriter {

    /**
     * The flag to determine is we should use inner parens for MultiPoint WKT.  The default is false.
     */
    private final boolean useInnerParensForMultiPoints;

    /**
     * Create a new WKTWriter
     */
    public WKTWriter() {
        this(false);
    }

    /**
     * Create a WKTWriter
     * @param useInnerParensForMultiPoints The flag to determine is we should use inner parens for MultiPoint WKT.
     */
    public WKTWriter(boolean useInnerParensForMultiPoints) {
        this.useInnerParensForMultiPoints = useInnerParensForMultiPoints;
    }

    /**
     * Write a Geometry to a WKT String
     * @param g The Geometry
     * @return A WKT String
     */
    public String write(Geometry g) {
        return write(g, true, true);
    }

    /**
     * Write a Geometry to a WKT String
     * @param g The Geometry
     * @param includeSrid Whether to include the SRID prefix (SRID=4326;) or not
     * @param includeDimension Whether to include the dimension (M, Z, or ZM) before the Geometry type
     * @return A WKT String
     */
    public String write(Geometry g, boolean includeSrid, boolean includeDimension) {
        if (g instanceof Point) {
            return write((Point) g, includeSrid, includeDimension);
        } else if (g instanceof LineString) {
            return write((LineString) g, includeSrid, includeDimension);
        } else if (g instanceof LinearRing) {
            return write((LinearRing) g, includeSrid, includeDimension);
        } else if (g instanceof Triangle) {
            return write((Triangle) g, includeSrid, includeDimension);
        } else if (g instanceof Polygon) {
            return write((Polygon) g, includeSrid, includeDimension);
        } else if (g instanceof MultiPoint) {
            return write((MultiPoint) g, includeSrid, includeDimension);
        } else if (g instanceof MultiLineString) {
            return write((MultiLineString) g, includeSrid, includeDimension);
        } else if (g instanceof MultiPolygon) {
            return write((MultiPolygon) g, includeSrid, includeDimension);
        } else if (g instanceof GeometryCollection) {
            return write((GeometryCollection) g, includeSrid, includeDimension);
        } else if (g instanceof CircularString) {
            return write((CircularString) g, includeSrid, includeDimension);
        } else if (g instanceof CurvePolygon) {
            return write((CurvePolygon) g, includeSrid, includeDimension);
        } else if (g instanceof MultiCurve) {
            return write((MultiCurve) g, includeSrid, includeDimension);
        } else if (g instanceof PolyHedralSurface) {
            return write((PolyHedralSurface) g, includeSrid, includeDimension);
        } else if (g instanceof MultiSurface) {
            return write((MultiSurface) g, includeSrid, includeDimension);
        } else if (g instanceof CompoundCurve) {
            return write((CompoundCurve) g, includeSrid, includeDimension);
        } else if (g instanceof Tin) {
            return write((Tin) g, includeSrid, includeDimension);
        }
        else {
            throw new IllegalArgumentException("Unknown Geometry! " + g.getClass().getName());
        }
    }

    public String write(Point p) {
        return write(p, true, true);
    }

    public String write(Point p, boolean includeSrid, boolean includeDimension) {
        Coordinate coordinate = p.getCoordinate();
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(p, builder);
        }
        builder.append("POINT");
        if (coordinate.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(p, builder);
            }
            builder.append(" (");
            addCoordinate(coordinate, builder);
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(LineString lineString) {
        return write(lineString, true, true);
    }

    public String write(LineString lineString, boolean includeSrid, boolean includeDimension) {
        List coordinates = lineString.getCoordinates();
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(lineString, builder);
        }
        builder.append("LINESTRING");
        if (coordinates.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(lineString, builder);
            }
            builder.append(" (");
            addCoordinates(coordinates, builder);
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(LinearRing ring) {
        return write(ring, true, true);
    }

    public String write(LinearRing lineString, boolean includeSrid, boolean includeDimension) {
        List coordinates = lineString.getCoordinates();
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(lineString, builder);
        }
        builder.append("LINEARRING");
        if (coordinates.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(lineString, builder);
            }
            builder.append(" (");
            addCoordinates(coordinates, builder);
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(MultiPoint mp) {
        return write(mp, true, true);
    }

    public String write(MultiPoint mp, boolean includeSrid, boolean includeDimension) {
        List<Point> points = mp.getPoints();
        List<Coordinate> coords = new ArrayList<Coordinate>();
        for(Point pt : points) {
            coords.add(pt.getCoordinate());
        }
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(mp, builder);
        }
        builder.append("MULTIPOINT");
        if (points.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(mp, builder);
            }
            builder.append(" (");
            addCoordinates(coords, useInnerParensForMultiPoints, builder);
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(Polygon p) {
        return write(p, true, true);
    }

    public String write(Polygon p, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(p, builder);
        }
        builder.append("POLYGON");
        if (p.getOuterLinearRing() == null || p.getOuterLinearRing().getCoordinates().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(p, builder);
            }
            builder.append(" ((");
            // Outer ring
            addCoordinates(p.getOuterLinearRing().getCoordinates(), builder);
            builder.append(")");
            // Inner rings
            for (LinearRing ring : p.getInnerLinearRings()) {
                builder.append(", (");
                addCoordinates(ring.getCoordinates(), builder);
                builder.append(")");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(MultiPolygon mp) {
        return write(mp, true, true);
    }

    public String write(MultiPolygon mp, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(mp, builder);
        }
        builder.append("MULTIPOLYGON");
        if (mp.getPolygons().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(mp, builder);
            }
            builder.append(" (");
            boolean firstPoly = true;
            for (Polygon polygon : mp.getPolygons()) {
                if (!firstPoly) {
                    builder.append(", ");
                } else {
                    firstPoly = false;
                }
                builder.append("((");
                // Outer ring
                addCoordinates(polygon.getOuterLinearRing().getCoordinates(), builder);
                builder.append(")");
                // Inner rings
                for (LinearRing ring : polygon.getInnerLinearRings()) {
                    builder.append(", (");
                    addCoordinates(ring.getCoordinates(), builder);
                    builder.append(")");
                }
                builder.append(")");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(MultiLineString ml) {
        return write(ml, true, true);
    }

    public String write(MultiLineString ml, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(ml, builder);
        }
        builder.append("MULTILINESTRING");
        if (ml.getLineStrings().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(ml, builder);
            }
            builder.append(" (");
            boolean firstLine = true;
            for (LineString line : ml.getLineStrings()) {
                if (!firstLine) {
                    builder.append(", ");
                } else {
                    firstLine = false;
                }
                builder.append("(");
                addCoordinates(line.getCoordinates(), builder);
                builder.append(")");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(GeometryCollection gc) {
        return write(gc, true, true);
    }

    public String write(GeometryCollection gc, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(gc, builder);
        }
        builder.append("GEOMETRYCOLLECTION");
        if (gc.getGeometries().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(gc, builder);
            }
            builder.append(" (");
            boolean first = true;
            for (Geometry g : gc.getGeometries()) {
                if (!first) {
                    builder.append(", ");
                } else {
                    first = false;
                }
                builder.append(write(g, false, false));
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(Triangle t) {
        return write(t, true, true);
    }

    public String write(Triangle t, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(t, builder);
        }
        builder.append("TRIANGLE");
        if (t.getOuterLinearRing().getCoordinates().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(t, builder);
            }
            builder.append(" ((");
            // Outer ring
            addCoordinates(t.getOuterLinearRing().getCoordinates(), builder);
            builder.append(")");
            // Inner rings
            for (LinearRing ring : t.getInnerLinearRings()) {
                builder.append(", (");
                addCoordinates(ring.getCoordinates(), builder);
                builder.append(")");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(CircularString circularString) {
        return write(circularString, true, true);
    }

    public String write(CircularString circularString, boolean includeSrid, boolean includeDimension) {
        List coordinates = circularString.getCoordinates();
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(circularString, builder);
        }
        builder.append("CIRCULARSTRING");
        if (coordinates.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(circularString, builder);
            }
            builder.append(" (");
            addCoordinates(coordinates, builder);
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(Tin tin) {
        return write(tin, true, true);
    }

    public String write(Tin tin, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(tin, builder);
        }
        builder.append("TIN");
        if (tin.getTriangles().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(tin, builder);
            }
            builder.append(" (");
            boolean first = true;
            for (Triangle t : tin.getTriangles()) {
                if (!first) {
                    builder.append(", ");
                } else {
                    first = false;
                }
                builder.append("((");
                // Outer ring
                addCoordinates(t.getOuterLinearRing().getCoordinates(), builder);
                builder.append(")");
                // Inner rings
                for (LinearRing ring : t.getInnerLinearRings()) {
                    builder.append(", (");
                    addCoordinates(ring.getCoordinates(), builder);
                    builder.append(")");
                }
                builder.append(")");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(CompoundCurve compoundCurve) {
        return write(compoundCurve, true, true);
    }

    public String write(CompoundCurve compoundCurve, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(compoundCurve, builder);
        }
        builder.append("COMPOUNDCURVE");
        if (compoundCurve.getCurves().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(compoundCurve, builder);
            }
            builder.append(" (");
            boolean first = true;
            for (Curve curve : compoundCurve.getCurves()) {
                if (!first) {
                    builder.append(", ");
                } else {
                    first = false;
                }
                if (curve instanceof LineString) {
                    builder.append("(");
                    addCoordinates(((LineString) curve).getCoordinates(), builder);
                    builder.append(")");
                } else if (curve instanceof CircularString) {
                    builder.append(write((CircularString) curve, false, false));
                }
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(CurvePolygon p) {
        return write(p, true, true);
    }

    public String write(CurvePolygon p, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(p, builder);
        }
        builder.append("CURVEPOLYGON");
        if (p.getOuterCurve() == null || p.getOuterCurve().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(p, builder);
            }
            builder.append(" (");
            // Outer ring
            Curve outerCurve = p.getOuterCurve();
            if (outerCurve instanceof LineString) {
                LineString line = (LineString) outerCurve;
                builder.append("(");
                addCoordinates(line.getCoordinates(), builder);
                builder.append(")");
            } else if (outerCurve instanceof CircularString) {
                builder.append(write((CircularString) outerCurve, false, false));
            } else if (outerCurve instanceof CompoundCurve) {
                builder.append(write((CompoundCurve) outerCurve, false, false));
            }
            builder.append(")");
            // Inner rings
            for (Curve curve : p.getInnerCurves()) {
                builder.append(", ");
                if (curve instanceof LineString) {
                    LineString line = (LineString) curve;
                    builder.append("(");
                    addCoordinates(line.getCoordinates(), builder);
                    builder.append(")");
                } else if (curve instanceof CircularString) {
                    builder.append(write((CircularString) curve, false, false));
                } else if (curve instanceof CompoundCurve) {
                    builder.append(write((CompoundCurve) curve, false, false));
                }
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(MultiCurve mc) {
        return write(mc, true, true);
    }

    public String write(MultiCurve mc, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(mc, builder);
        }
        builder.append("MULTICURVE");
        if (mc.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(mc, builder);
            }
            builder.append(" (");
            boolean first = true;
            for (Curve c : mc.getCurves()) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                if (c instanceof LineString) {
                    LineString line = (LineString) c;
                    builder.append("(");
                    addCoordinates(line.getCoordinates(), builder);
                    builder.append(")");
                } else if (c instanceof CircularString) {
                    builder.append(write((CircularString) c, false, false));
                } else if (c instanceof CompoundCurve) {
                    builder.append(write((CompoundCurve) c, false, false));
                }
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(PolyHedralSurface phs) {
        return write(phs, true, true);
    }

    public String write(PolyHedralSurface phs, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(phs, builder);
        }
        builder.append("POLYHEDRALSURFACE");
        if (phs.getPolygons().isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(phs, builder);
            }
            builder.append(" (");
            boolean firstPoly = true;
            for (Polygon polygon : phs.getPolygons()) {
                if (!firstPoly) {
                    builder.append(", ");
                } else {
                    firstPoly = false;
                }
                builder.append("((");
                // Outer ring
                addCoordinates(polygon.getOuterLinearRing().getCoordinates(), builder);
                builder.append(")");
                // Inner rings
                for (LinearRing ring : polygon.getInnerLinearRings()) {
                    builder.append(", (");
                    addCoordinates(ring.getCoordinates(), builder);
                    builder.append(")");
                }
                builder.append(")");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    public String write(MultiSurface ms) {
        return write(ms, true, true);
    }

    public String write(MultiSurface ms, boolean includeSrid, boolean includeDimension) {
        StringBuilder builder = new StringBuilder();
        if (includeSrid) {
            addSrid(ms, builder);
        }
        builder.append("MULTISURFACE");
        if (ms.isEmpty()) {
            builder.append(" EMPTY");
        } else {
            if (includeDimension) {
                addDimension(ms, builder);
            }
            builder.append(" (");
            boolean first = true;
            for (Surface s : ms.getSurfaces()) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                if (s instanceof Polygon) {
                    addPolygonText((Polygon) s, builder);
                } else if (s instanceof CurvePolygon) {
                    builder.append(write((CurvePolygon) s, false, false));
                }
            }
            builder.append(")");
        }
        return builder.toString();
    }

    protected void addCoordinates(List<Coordinate> coordinates, StringBuilder builder) {
        addCoordinates(coordinates, false, builder);
    }

    protected void addCoordinates(List<Coordinate> coordinates, boolean innerParens, StringBuilder builder) {
        boolean first = true;
        for (Coordinate coordinate : coordinates) {
            if (!first) {
                builder.append(", ");
            } else {
                first = false;
            }
            if (innerParens) {
                builder.append("(");
            }
            addCoordinate(coordinate, builder);
            if (innerParens) {
                builder.append(")");
            }
        }
    }

    /**
     * Add a Coordinate to the StringBuilder
     * @param coordinate The Coordinate
     * @param builder The StringBuilder
     */
    protected void addCoordinate(Coordinate coordinate, StringBuilder builder) {
        builder.append(coordinate.getX());
        builder.append(" ");
        builder.append(coordinate.getY());
        if (coordinate.getDimension() == Dimension.TwoMeasured) {
            builder.append(" ").append(coordinate.getM());
        } else if (coordinate.getDimension() == Dimension.Three) {
            builder.append(" ").append(coordinate.getZ());
        } else if (coordinate.getDimension() == Dimension.ThreeMeasured) {
            builder.append(" ").append(coordinate.getZ());
            builder.append(" ").append(coordinate.getM());
        }
    }

    /**
     * Add the SRID to the StringBuilder
     * @param g The Geometry
     * @param builder The StringBuilder
     */
    protected void addSrid(Geometry g, StringBuilder builder) {
        if (g.getSrid() != null) {
            builder.append("SRID=").append(g.getSrid()).append(";");
        }
    }

    /**
     * Add the Dimension (Z, M, or ZM) to the StringBuilder
     * @param g The Geometry
     * @param builder The StringBuilder
     */
    protected void addDimension(Geometry g, StringBuilder builder) {
        if (g.getDimension() == Dimension.TwoMeasured) {
            builder.append(" M");
        } else if (g.getDimension() == Dimension.Three) {
            builder.append(" Z");
        } else if (g.getDimension() == Dimension.ThreeMeasured) {
            builder.append(" ZM");
        }
    }

    /**
     * Add Polygon outer ring and inner rings to the StringBuilder
     * @param polygon The Polygon
     * @param builder The StringBuilder
     */
    protected void addPolygonText(Polygon polygon, StringBuilder builder) {
        builder.append("((");
        // Outer ring
        addCoordinates(polygon.getOuterLinearRing().getCoordinates(), builder);
        builder.append(")");
        // Inner rings
        for (LinearRing ring : polygon.getInnerLinearRings()) {
            builder.append(", (");
            addCoordinates(ring.getCoordinates(), builder);
            builder.append(")");
        }
        builder.append(")");
    }
}
