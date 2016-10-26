package org.cugos.parboiledwkt;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;

import java.util.ArrayList;
import java.util.List;

/**
 * The Parboiled WKTParser.
 * @author Jared Erickson
 */
@BuildParseTree
public class WKTParser extends BaseParser<Object> {

    /**
     * The Dimension for all Geometry can only be set once per parse.
     */
    protected Dimension dimension;

    /**
     * The default SRID which is set once per parse.
     */
    protected String defaultSrid = null;

    /**
     * The only public Rule, this is the starting point for parsing WKT.
     * For every parse, we need to reset the Dimension and SRID
     *
     * @return The WKT Rule
     */
    public Rule WKT() {
        return Sequence(
                resetDimension(),
                resetSrid(),
                Geometry()
        );
    }

    Rule Geometry() {
        return FirstOf(
                Point(),
                LineString(),
                LinearRing(),
                Polygon(),
                MultiPoint(),
                MultiLineString(),
                MultiPolygon(),
                GeometryCollection(),
                Triangle(),
                CircularString(),
                Tin(),
                CompoundCurve(),
                CurvePolygon(),
                MultiCurve(),
                PolyHedralSurface(),
                MultiSurface()
        );
    }

    Rule Srid() {
        return Sequence(
                "SRID=",
                Digit(),
                push(match()),
                ";"
        );
    }

    Rule Point() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "POINT",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new Point(new Coordinate(Double.NaN, Double.NaN, Double.NaN, Double.NaN), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                Coordinate(),
                                ")",
                                push(new Point((Coordinate) pop(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule LineString() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "LINESTRING",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new LineString(new ArrayList<Coordinate>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                Coordinates(),
                                ")",
                                push(new LineString((List<Coordinate>) pop(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule LineStringCoordinates() {
        return Sequence(
                "(",
                Coordinates(),
                ")",
                push(new LineString((List<Coordinate>) pop(), dimension, defaultSrid))
        );
    }

    Rule Tin() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "TIN",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new Tin(new ArrayList<Triangle>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                CoordinateSetsSets(),
                                ")",
                                push(new Tin(getTriangles((List<List<List<Coordinate>>>) pop(), dimension, srid.get()), dimension, srid.get()))
                        )
                )
        );
    }

    Rule LinearRing() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "LINEARRING",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new LineString(new ArrayList<Coordinate>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                Coordinates(),
                                ")",
                                push(new LineString((List<Coordinate>) pop(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule Polygon() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "POLYGON",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new Polygon(
                                        new LinearRing(new ArrayList<Coordinate>(), Dimension.Two, srid.get()),
                                        new ArrayList<LinearRing>(),
                                        Dimension.Two,
                                        srid.get()
                                ))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                CoordinateSets(),
                                ")",
                                push(new Polygon(
                                                new LinearRing(head((List<List<Coordinate>>) peek()), dimension, srid.get()),
                                                createRings(tail((List<List<Coordinate>>) pop()), dimension, srid.get()),
                                                dimension,
                                                srid.get()
                                        )
                                )
                        )
                )
        );
    }

    Rule PolygonText() {
        return Sequence(
                "(",
                CoordinateSets(),
                ")",
                push(new Polygon(
                                new LinearRing(head((List<List<Coordinate>>) peek()), dimension, defaultSrid),
                                createRings(tail((List<List<Coordinate>>) pop()), dimension, defaultSrid),
                                dimension,
                                defaultSrid
                        )
                )
        );
    }

    Rule MultiPoint() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "MULTIPOINT",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new MultiPoint(new ArrayList<Point>(), dimension, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                push(dimension),
                                FirstOf(
                                        CoordinateSets(),
                                        Coordinates()
                                ),
                                ")",
                                push(new MultiPoint((List<Point>) flatten((List<?>) pop()), dimension, srid.get()))
                        )
                )

        );
    }

    Rule MultiLineString() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "MULTILINESTRING",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new MultiLineString(new ArrayList<LineString>(), dimension, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        CoordinateSets()
                                ),
                                ")",
                                push(new MultiLineString(getLineStrings((List<List<Coordinate>>) pop(), dimension, srid.get()), dimension, srid.get()))
                        )
                )
        );
    }

    Rule MultiPolygon() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "MULTIPOLYGON",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new MultiPolygon(new ArrayList<Polygon>(), dimension, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        CoordinateSetsSets()
                                ),
                                ")",
                                push(new MultiPolygon(getPolygons((List<List<List<Coordinate>>>) pop(), dimension, srid.get()), dimension, srid.get()))
                        )
                )
        );
    }

    Rule GeometryCollection() {
        Var<String> srid = new Var<String>(defaultSrid);
        Var<List<Geometry>> geometries = new Var<List<Geometry>>(new ArrayList<Geometry>());
        return Sequence(
                Optional(Srid(), srid.set((String) pop()), setDefaultSrid(srid.get())),
                "GEOMETRYCOLLECTION",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new GeometryCollection(new ArrayList<Geometry>(), dimension, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        Geometry(),
                                        ACTION(geometries.get().add((Geometry) pop())),
                                        Optional(Ch(' ')),
                                        Optional(Ch(',')),
                                        Optional(Ch(' '))
                                ),
                                ")",
                                push(new GeometryCollection(geometries.get(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule CircularString() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "CIRCULARSTRING",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new CircularString(new ArrayList<Coordinate>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                Coordinates(),
                                ")",
                                push(new CircularString((List<Coordinate>) pop(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule CurvePolygon() {
        Var<String> srid = new Var<String>(defaultSrid);
        Var<List<Curve>> curves = new Var<List<Curve>>(new ArrayList<Curve>());
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "CURVEPOLYGON",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new CurvePolygon(
                                                new LineString(new ArrayList<Coordinate>(), Dimension.Two, srid.get()),
                                                new ArrayList<Curve>(),
                                                Dimension.Two,
                                                srid.get())
                                )
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        FirstOf(
                                                CircularString(),
                                                LineStringCoordinates(),
                                                CompoundCurve()
                                        ),
                                        ACTION(curves.get().add((Curve) pop())),
                                        Optional(Ch(' ')),
                                        Optional(Ch(',')),
                                        Optional(Ch(' '))
                                ),
                                ")",
                                push(new CurvePolygon(
                                                headOfCurves(curves.get()),
                                                tailOfCurves(curves.get()),
                                                dimension,
                                                srid.get())
                                )
                        )
                )
        );
    }

    Rule Triangle() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "TRIANGLE",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new Triangle(
                                        new LinearRing(new ArrayList<Coordinate>(), Dimension.Two, srid.get()),
                                        new ArrayList<LinearRing>(),
                                        Dimension.Two,
                                        srid.get()
                                ))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                CoordinateSets(),
                                ")",
                                push(new Triangle(
                                                new LinearRing(head((List<List<Coordinate>>) peek()), dimension, srid.get()),
                                                createRings(tail((List<List<Coordinate>>) pop()), dimension, srid.get()),
                                                dimension,
                                                srid.get()
                                        )
                                )
                        )
                )
        );
    }

    Rule MultiSurface() {
        Var<String> srid = new Var<String>(defaultSrid);
        Var<List<Surface>> surfaces = new Var<List<Surface>>(new ArrayList<Surface>());
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "MULTISURFACE",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new MultiSurface(new ArrayList<Surface>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        FirstOf(
                                                PolygonText(),
                                                CurvePolygon()
                                        ),
                                        ACTION(surfaces.get().add((Surface) pop())),
                                        Optional(Ch(' ')),
                                        Optional(Ch(',')),
                                        Optional(Ch(' '))
                                ),
                                ")",
                                push(new MultiSurface(surfaces.get(), dimension, srid.get()))
                        )
                )
        );
    }



    Rule MultiCurve() {
        Var<String> srid = new Var<String>(defaultSrid);
        Var<List<Curve>> curves = new Var<List<Curve>>(new ArrayList<Curve>());
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "MULTICURVE",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new MultiCurve(new ArrayList<Curve>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        FirstOf(
                                                CircularString(),
                                                LineStringCoordinates(),
                                                CompoundCurve()
                                        ),
                                        ACTION(curves.get().add((Curve) pop())),
                                        Optional(Ch(' ')),
                                        Optional(Ch(',')),
                                        Optional(Ch(' '))
                                ),
                                ")",
                                push(new MultiCurve(curves.get(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule CompoundCurve() {
        Var<String> srid = new Var<String>(defaultSrid);
        Var<List<Curve>> curves = new Var<List<Curve>>(new ArrayList<Curve>());
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "COMPOUNDCURVE",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new CompoundCurve(new ArrayList<Curve>(), Dimension.Two, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        FirstOf(
                                                CircularString(),
                                                LineStringCoordinates()
                                        ),
                                        ACTION(curves.get().add((Curve) pop())),
                                        Optional(Ch(' ')),
                                        Optional(Ch(',')),
                                        Optional(Ch(' '))
                                ),
                                ")",
                                push(new CompoundCurve(curves.get(), dimension, srid.get()))
                        )
                )
        );
    }

    Rule PolyHedralSurface() {
        Var<String> srid = new Var<String>(defaultSrid);
        return Sequence(
                Optional(Srid(), srid.set((String) pop())),
                "POLYHEDRALSURFACE",
                Optional(Ch(' ')),
                FirstOf(
                        Sequence(
                                "EMPTY",
                                push(new PolyHedralSurface(new ArrayList<Polygon>(), dimension, srid.get()))
                        ),
                        Sequence(
                                Optional("ZM", setDimension(Dimension.ThreeMeasured)),
                                Optional(Ch('Z'), setDimension(Dimension.Three)),
                                Optional(Ch('M'), setDimension(Dimension.TwoMeasured)),
                                setDimension(Dimension.Two),
                                Optional(Ch(' ')),
                                "(",
                                OneOrMore(
                                        CoordinateSetsSets()
                                ),
                                ")",
                                push(new PolyHedralSurface(getPolygons((List<List<List<Coordinate>>>) pop(), dimension, srid.get()), dimension, srid.get()))
                        )
                )
        );
    }

    // ((35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30))
    Rule CoordinateSetsSets() {
        Var<List<List<List<Coordinate>>>> coordsListsList =
                new Var<List<List<List<Coordinate>>>>(new ArrayList<List<List<Coordinate>>>());
        return Sequence(OneOrMore(
                        "(",
                        CoordinateSets(),
                        ")",
                        Optional(Ch(' ')),
                        Optional(Ch(',')),
                        Optional(Ch(' ')),
                        ACTION(coordsListsList.get().add((List<List<Coordinate>>) pop()))
                ),
                push(coordsListsList.get())
        );
    }

    // (35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30)
    Rule CoordinateSets() {
        Var<List<List<Coordinate>>> coordsLists = new Var<List<List<Coordinate>>>(new ArrayList<List<Coordinate>>());
        return Sequence(OneOrMore(
                        "(",
                        Coordinates(),
                        ")",
                        Optional(Ch(' ')),
                        Optional(Ch(',')),
                        Optional(Ch(' ')),
                        ACTION(coordsLists.get().add((List<Coordinate>) pop()))
                ),
                push(coordsLists.get())
        );
    }

    // 30 10, 10 30, 40 40
    Rule Coordinates() {
        Var<List<Coordinate>> coords = new Var<List<Coordinate>>(new ArrayList<Coordinate>());
        return Sequence(OneOrMore(
                        Coordinate(),
                        Optional(Ch(' ')),
                        Optional(Ch(',')),
                        Optional(Ch(' ')),
                        ACTION(coords.get().add((Coordinate) pop()))
                ),
                push(coords.get())
        );
    }

    // 30 10
    Rule Coordinate() {
        Var<Coordinate.Builder> coordBuilder = new Var<Coordinate.Builder>(new Coordinate.Builder());
        return Sequence(
                // X
                Number(),
                setCoordinateValue("x", dimension, coordBuilder.get(), match()),
                Ch(' '),
                // Y
                Number(),
                setCoordinateValue("y", dimension, coordBuilder.get(), match()),
                Optional(Ch(' ')),
                // M or Z
                Optional(Number(), setCoordinateValue("zOrM", dimension, coordBuilder.get(), match())),
                Optional(Ch(' ')),
                // M
                Optional(Number(), setCoordinateValue("m", dimension, coordBuilder.get(), match())),
                push(coordBuilder.get().build())
        );
    }

    Rule Digit() {
        return OneOrMore(CharRange('0', '9'));
    }

    Rule Number() {
        return Sequence(Optional(AnyOf("-+")), OneOrMore(Digit()),
                Optional('.', ZeroOrMore(Digit())));
    }

    protected boolean resetSrid() {
        this.defaultSrid = null;
        return true;
    }

    protected boolean setDefaultSrid(String srid) {
        this.defaultSrid = srid;
        return true;
    }


    /**
     * Set the Dimension but only if it is null.
     * @param d The Dimension
     * @return true so it can be used as a parse action
     */
    protected boolean setDimension(Dimension d) {
        if (this.dimension == null) {
            this.dimension = d;
        }
        return true;
    }

    /**
     * Reset the Dimension by setting it to null.
     * @return true so it can be used as a parse action
     */
    protected boolean resetDimension() {
        this.dimension = null;
        return true;
    }

    protected List<Polygon> getPolygons(List<List<List<Coordinate>>> coordinateSetsSets, Dimension dimension, String srid) {
        List<Polygon> polygons = new ArrayList<Polygon>();
        for (List<List<Coordinate>> coordinateSets : coordinateSetsSets) {
            LinearRing outerRing = new LinearRing(head(coordinateSets), dimension, srid);
            List<LinearRing> innerRings = createRings(tail(coordinateSets), dimension, srid);
            polygons.add(
                    new Polygon(
                            outerRing,
                            innerRings,
                            dimension,
                            srid
                    )
            );
        }
        return polygons;
    }

    protected List<LineString> getLineStrings(List<List<Coordinate>> coordinateSets, Dimension dimension, String srid) {
        List<LineString> lines = new ArrayList<LineString>();
        for (List<Coordinate> coordinates : coordinateSets) {
            lines.add(new LineString(coordinates, dimension, srid));
        }
        return lines;
    }


    protected List<Coordinate> head(List<List<Coordinate>> coordinateSet) {
        List<Coordinate> coords = new ArrayList<Coordinate>();
        if (coordinateSet.size() > 0) {
            coords.addAll(coordinateSet.get(0));
        }
        return coords;
    }

    protected List<List<Coordinate>> tail(List<List<Coordinate>> coordinateSet) {
        List<List<Coordinate>> tailCoordinateSet = new ArrayList<List<Coordinate>>();
        if (coordinateSet.size() > 1) {
            tailCoordinateSet.addAll(coordinateSet.subList(1, coordinateSet.size()));
        }
        return tailCoordinateSet;
    }

    protected List<LinearRing> createRings(List<List<Coordinate>> coordinateSet, Dimension dimension, String srid) {
        List<LinearRing> rings = new ArrayList<LinearRing>();
        for (List<Coordinate> coords : coordinateSet) {
            rings.add(new LinearRing(coords, dimension, srid));
        }
        return rings;
    }

    // Turn a CoordinateSet (List of Lists of Coordinates) into a List of Coordinates
    protected List<Point> flatten(List<?> coords) {
        List<Point> flattenedPoints = new ArrayList<Point>();
        for (Object obj : coords) {
            if (obj instanceof Coordinate) {
                Coordinate c = (Coordinate)obj;
                flattenedPoints.add(new Point(c, c.getDimension()));
            } else if (obj instanceof List) {
                for (Object obj2 : (List) obj) {
                    Coordinate c = (Coordinate)obj2;
                    flattenedPoints.add(new Point(c, c.getDimension()));
                }
            }
        }
        return flattenedPoints;
    }

    protected Curve headOfCurves(List<Curve> curves) {
        if (!curves.isEmpty()) {
            return curves.get(0);
        } else {
            return null;
        }
    }

    protected List<Curve> tailOfCurves(List<Curve> curves) {
        List<Curve> curvesTail = new ArrayList<Curve>();
        if (curves.size() > 1) {
            curvesTail.addAll(curves.subList(1, curves.size()));
        }
        return curvesTail;
    }

    protected List<Triangle> getTriangles(List<List<List<Coordinate>>> coordinateListsLists, Dimension dimension, String srid) {
        List<Triangle> triangles = new ArrayList<Triangle>();
        for (List<List<Coordinate>> coordinateLists : coordinateListsLists) {
            triangles.add(new Triangle(
                    new LinearRing(head(coordinateLists), dimension, srid),
                    createRings(tail(coordinateLists), dimension, srid),
                    dimension, srid));
        }
        return triangles;
    }

    protected boolean setCoordinateValue(String type, Dimension dim, Coordinate.Builder coord, String value) {
        if (type.equalsIgnoreCase("x")) {
            coord.setX(Double.parseDouble(value));
        } else if (type.equalsIgnoreCase("y")) {
            coord.setY(Double.parseDouble(value));
        } else if (type.equalsIgnoreCase("zOrM")) {
            if (dim == Dimension.TwoMeasured) {
                coord.setM(Double.parseDouble(value));
            } else {
                // Support for POINT(0 0 5) which should be POINT Z (0 0 5)
                if (dimension == Dimension.Two) {
                    this.dimension = Dimension.Three;
                }
                coord.setZ(Double.parseDouble(value));
            }
        } else if (type.equalsIgnoreCase("m")) {
            // Support for POINT(0 0 5 4) which should be POINT ZM (0 0 5 4)
            if (dimension == Dimension.Three) {
                dimension = Dimension.ThreeMeasured;
            }
            coord.setM(Double.parseDouble(value));
        }
        return true;
    }

}
