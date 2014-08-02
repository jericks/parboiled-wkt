package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void empty() {
        String wkt = "TRIANGLE EMPTY";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertTrue(t.getOuterLinearRing().getCoordinates().isEmpty());
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        // WKT
        assertEquals("TRIANGLE EMPTY", t.toString());
    }

    @Test
    public void twoDimensional() {
        String wkt = "TRIANGLE((0 0 ,0 1,1 1,0 0))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 1), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))", t.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;TRIANGLE((0 0 ,0 1,1 1,0 0))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals("4326", t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 1), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("SRID=4326;TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))", t.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "TRIANGLE M ((0 0 0,0 1 0,1 1 0,0 0 0))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.TwoMeasured, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create2DM(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(0, 1, 0), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(1, 1, 0), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE M ((0.0 0.0 0.0, 0.0 1.0 0.0, 1.0 1.0 0.0, 0.0 0.0 0.0))", t.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "TRIANGLE Z ((0 0 0,0 1 0,1 1 0,0 0 0))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.Three, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(0, 1, 0), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 1, 0), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE Z ((0.0 0.0 0.0, 0.0 1.0 0.0, 1.0 1.0 0.0, 0.0 0.0 0.0))", t.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "TRIANGLE ZM ((0 0 0 1,0 1 0 2,1 1 0 3,0 0 0 1))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.ThreeMeasured, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 0, 1), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(0, 1, 0, 2), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(1, 1, 0, 3), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 0, 0, 1), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE ZM ((0.0 0.0 0.0 1.0, 0.0 1.0 0.0 2.0, 1.0 1.0 0.0 3.0, 0.0 0.0 0.0 1.0))", t.toString());
    }

    @Test
    public void threeDimensionalWithoutZ() {
        String wkt = "TRIANGLE((0 0 0,0 1 0,1 1 0,0 0 0))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.Three, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(0, 1, 0), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 1, 0), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE Z ((0.0 0.0 0.0, 0.0 1.0 0.0, 1.0 1.0 0.0, 0.0 0.0 0.0))", t.toString());
    }

}
