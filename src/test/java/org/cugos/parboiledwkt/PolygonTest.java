package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class PolygonTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POLYGON EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertTrue(p.getOuterLinearRing().getCoordinates().isEmpty());
        assertTrue(p.getInnerLinearRings().isEmpty());
        assertEquals("POLYGON EMPTY", p.toString());
        assertEquals(0, p.getNumberOfCoordinates());
    }

    @Test
    public void twoDimensionalWithHoles() {
        String wkt = "POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10),(20 30, 35 35, 30 20, 20 30))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertNull(p.getSrid());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(9, p.getNumberOfCoordinates());
        assertEquals(5, p.getOuterLinearRing().getCoordinates().size());
        assertEquals(1, p.getInnerLinearRings().size());
        // Outer Ring
        assertEquals(Coordinate.create2D(35, 10), p.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(45, 45), p.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(15, 40), p.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(10, 20), p.getOuterLinearRing().getCoordinates().get(3));
        assertEquals(Coordinate.create2D(35, 10), p.getOuterLinearRing().getCoordinates().get(4));
        // Inner Ring: 0
        LinearRing ring = p.getInnerLinearRings().get(0);
        assertEquals(4, ring.getCoordinates().size());
        assertEquals(Coordinate.create2D(20, 30), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(35, 35), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(30, 20), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(20, 30), ring.getCoordinates().get(3));
        // WKT
        assertEquals("POLYGON ((35.0 10.0, 45.0 45.0, 15.0 40.0, 10.0 20.0, 35.0 10.0), (20.0 30.0, 35.0 35.0, 30.0 20.0, 20.0 30.0))", p.toString());
    }

    @Test
    public void twoDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertNull(p.getSrid());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(5, p.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, p.getInnerLinearRings().size());
        // Outer Ring
        assertEquals(Coordinate.create2D(30, 10), p.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(40, 40), p.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(20, 40), p.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(10, 20), p.getOuterLinearRing().getCoordinates().get(3));
        assertEquals(Coordinate.create2D(30, 10), p.getOuterLinearRing().getCoordinates().get(4));
        // WKT
        assertEquals("POLYGON ((30.0 10.0, 40.0 40.0, 20.0 40.0, 10.0 20.0, 30.0 10.0))", p.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("SRID=4326;POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertEquals("4326", p.getSrid());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(5, p.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, p.getInnerLinearRings().size());
        // Outer Ring
        assertEquals(Coordinate.create2D(30, 10), p.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(40, 40), p.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(20, 40), p.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(10, 20), p.getOuterLinearRing().getCoordinates().get(3));
        assertEquals(Coordinate.create2D(30, 10), p.getOuterLinearRing().getCoordinates().get(4));
        // WKT
        assertEquals("SRID=4326;POLYGON ((30.0 10.0, 40.0 40.0, 20.0 40.0, 10.0 20.0, 30.0 10.0))", p.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POLYGON M ((30 10 1, 40 40 2, 20 40 3, 10 20 4, 30 10 1))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertNull(p.getSrid());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(5, p.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, p.getInnerLinearRings().size());
        // Outer Ring
        assertEquals(Coordinate.create2DM(30, 10, 1), p.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(40, 40, 2), p.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(20, 40, 3), p.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(10, 20, 4), p.getOuterLinearRing().getCoordinates().get(3));
        assertEquals(Coordinate.create2DM(30, 10, 1), p.getOuterLinearRing().getCoordinates().get(4));
        // WKT
        assertEquals("POLYGON M ((30.0 10.0 1.0, 40.0 40.0 2.0, 20.0 40.0 3.0, 10.0 20.0 4.0, 30.0 10.0 1.0))", p.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POLYGON Z ((30 10 1, 40 40 2, 20 40 3, 10 20 4, 30 10 1))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertNull(p.getSrid());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(5, p.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, p.getInnerLinearRings().size());
        // Outer Ring
        assertEquals(Coordinate.create3D(30, 10, 1), p.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(40, 40, 2), p.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(20, 40, 3), p.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(10, 20, 4), p.getOuterLinearRing().getCoordinates().get(3));
        assertEquals(Coordinate.create3D(30, 10, 1), p.getOuterLinearRing().getCoordinates().get(4));
        // WKT
        assertEquals("POLYGON Z ((30.0 10.0 1.0, 40.0 40.0 2.0, 20.0 40.0 3.0, 10.0 20.0 4.0, 30.0 10.0 1.0))", p.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POLYGON ZM ((30 10 1 2, 40 40 2 4, 20 40 3 5, 10 20 4 6, 30 10 1 2))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Polygon);
        Polygon p = (Polygon) geometry;
        assertNull(p.getSrid());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(5, p.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, p.getInnerLinearRings().size());
        // Outer Ring
        assertEquals(Coordinate.create3DM(30, 10, 1, 2), p.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(40, 40, 2, 4), p.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(20, 40, 3, 5), p.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(10, 20, 4, 6), p.getOuterLinearRing().getCoordinates().get(3));
        assertEquals(Coordinate.create3DM(30, 10, 1, 2), p.getOuterLinearRing().getCoordinates().get(4));
        // WKT
        assertEquals("POLYGON ZM ((30.0 10.0 1.0 2.0, 40.0 40.0 2.0 4.0, 20.0 40.0 3.0 5.0, 10.0 20.0 4.0 6.0, 30.0 10.0 1.0 2.0))", p.toString());
    }
}
