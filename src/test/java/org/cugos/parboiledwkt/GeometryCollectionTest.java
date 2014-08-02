package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class GeometryCollectionTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("GEOMETRYCOLLECTION EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertNull(gc.getSrid());
        assertTrue(gc.getGeometries().isEmpty());
        // WKT
        assertEquals("GEOMETRYCOLLECTION EMPTY", gc.toString());
    }

    @Test
    public void twoDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertNull(gc.getSrid());
        assertEquals(Dimension.Two, gc.getDimension());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.Two, point.getDimension());
        assertEquals(Coordinate.create2D(4, 6), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(4, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(7, 10), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION (POINT (4.0 6.0), LINESTRING (4.0 6.0, 7.0 10.0))", gc.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("SRID=4326;GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals("4326", gc.getSrid());
        assertEquals(Dimension.Two, gc.getDimension());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals("4326", point.getSrid());
        assertEquals(Dimension.Two, point.getDimension());
        assertEquals(Coordinate.create2D(4, 6), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals("4326", line.getSrid());
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(4, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(7, 10), line.getCoordinates().get(1));
        // WKT
        assertEquals("SRID=4326;GEOMETRYCOLLECTION (POINT (4.0 6.0), LINESTRING (4.0 6.0, 7.0 10.0))", gc.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("GEOMETRYCOLLECTION M (POINT(4 6 3),LINESTRING(4 6 2,7 10 4))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals(Dimension.TwoMeasured, gc.getDimension());
        assertNull(gc.getSrid());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.TwoMeasured, point.getDimension());
        assertEquals(Coordinate.create2DM(4, 6, 3), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.TwoMeasured, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(4, 6, 2), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(7, 10, 4), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION M (POINT (4.0 6.0 3.0), LINESTRING (4.0 6.0 2.0, 7.0 10.0 4.0))", gc.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("GEOMETRYCOLLECTION Z (POINT(4 6 3),LINESTRING(4 6 2,7 10 4))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals(Dimension.Three, gc.getDimension());
        assertNull(gc.getSrid());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.Three, point.getDimension());
        assertEquals(Coordinate.create3D(4, 6, 3), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.Three, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(4, 6, 2), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(7, 10, 4), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION Z (POINT (4.0 6.0 3.0), LINESTRING (4.0 6.0 2.0, 7.0 10.0 4.0))", gc.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("GEOMETRYCOLLECTION ZM (POINT(4 6 3 1.2),LINESTRING(4 6 2 3.4,7 10 4 5.6))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals(Dimension.ThreeMeasured, gc.getDimension());
        assertNull(gc.getSrid());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.ThreeMeasured, point.getDimension());
        assertEquals(Coordinate.create3DM(4, 6, 3, 1.2), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.ThreeMeasured, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(4, 6, 2, 3.4), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(7, 10, 4, 5.6), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION ZM (POINT (4.0 6.0 3.0 1.2), LINESTRING (4.0 6.0 2.0 3.4, 7.0 10.0 4.0 5.6))", gc.toString());
    }
}

