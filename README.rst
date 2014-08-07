Parboiled WKT
============
Parboiled WKT is a Java Library that uses the `Parboiled 1.x <https://github.com/sirthias/parboiled/wiki>`_ Library to parse WKT.

It does not use the awesome Java Topology Suite(JTS) because it parses Geometry types that JTS does not yet support.  It also can parse EWKT or WKT with a spatial reference system identifier. Finally, it can parse Geometry with XY, XYZ, XYM, or XYZM coordinates.::

    POINT (1 1)

    POINT Z (1 2 3)

    POINT M (1 2 3)

    POINT ZM (1 2 3 4)

    SRID=4326;POINT (1 1)

    LINESTRING (101 234,345 567)

    POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10),(20 30, 35 35, 30 20, 20 30))

    MULTIPOINT (10 40, 40 30, 20 20, 30 10)

    MULTIPOINT ((10 40), (40 30), (20 20), (30 10))

    MULTILINESTRING ((0.0 0.0, 1.0 1.0, 1.0 2.0), (2.0 3.0, 3.0 2.0, 5.0 4.0))

    MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)), ((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20)))

    GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))

    CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)

    COMPOUNDCURVE(CIRCULARSTRING(1 0, 0 1, -1 0), (-1 0, 2 0))

    CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1))

    MULTICURVE ((5.0 5.0, 3.0 5.0, 3.0 3.0, 0.0 3.0), CIRCULARSTRING (0.0 0.0, 2.0 1.0, 2.0 2.0))

    MULTISURFACE(CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1)),((10 10, 14 12, 11 10, 10 10),(11 11, 11.5 11, 11 11.5, 11 11)))

    POLYHEDRALSURFACE (((40 40, 20 45, 45 30, 40 40)),((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20)))

    TIN (((0 0, 1 0, 0 1, 0 0)), ((0 0, 0 1, 1 1, 0 0)))

    TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))

Use
---
The low level parboiled parser is **org.cugos.parboiledwkt.WKTParser**::

    import org.cugos.parboiledwkt.WKTParser;
    import org.cugos.parboiledwkt.Geometry;
    import org.parboiled.Parboiled;
    import org.parboiled.parserunners.ReportingParseRunner;

    WKTParser parser = Parboiled.createParser(WKTParser.class);
    ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT (1 2)");
    Geometry geometry = result.resultValue;

Most often you will want to use **org.cugos.parboiledwkt.WTKReader** which has a much simpler interface::

    import org.cugos.parboiledwkt.WKTReader;
    import org.cugos.parboiledwkt.Point;

    String wkt = "POINT (1.0 2.0)";
    WKTReader reader = new WKTReader();
    Point point = (Point) reader.read(wkt);

Once you have parse a WKT String into a **org.cugos.parboiledwkt.Geometry** you can write it back to WKT using the **org.cugos.parboiledwkt.WKTWriter**::

    import org.cugos.parboiledwkt.WKTWriter;
    import org.cugos.parboiledwkt.Coordinate;
    import org.cugos.parboiledwkt.Dimension;
    import org.cugos.parboiledwkt.CircularString;

    WKTWriter writer = new WKTWriter();
    CircularString cs = new CircularString(Arrays.asList(
        Coordinate.create2D(1, 1),
        Coordinate.create2D(5, 5),
        Coordinate.create2D(2, 2)),
        Dimension.Two)
    )
    String wkt = writer.write(cs);

Licene
------
Parboiled WKT is open source and licensed under the MIT License.
