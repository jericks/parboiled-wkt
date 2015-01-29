package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class MultiCurveTest {

    @Test
    public void empty() {
        String wkt = "MULTICURVE EMPTY";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiCurve);
        MultiCurve mc = (MultiCurve) geometry;
        assertTrue(mc.isEmpty());
        assertNull(mc.getSrid());
        assertEquals(Dimension.Two, mc.getDimension());
        // WKT
        assertEquals("MULTICURVE EMPTY", mc.toString());
    }

    @Test
    public void twoDimensional() {
        String wkt = "MULTICURVE((5 5,3 5,3 3,0 3),CIRCULARSTRING(0 0,2 1,2 2))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiCurve);
        MultiCurve mc = (MultiCurve) geometry;
        assertFalse(mc.isEmpty());
        assertEquals(7, mc.getNumberOfCoordinates());
        assertNull(mc.getSrid());
        assertEquals(Dimension.Two, mc.getDimension());
        assertEquals(2, mc.getCurves().size());
        // 0
        assertTrue(mc.getCurves().get(0) instanceof LineString);
        LineString line = (LineString) mc.getCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(5, 5), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 5), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(3, 3), line.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 3), line.getCoordinates().get(3));
        // 1
        assertTrue(mc.getCurves().get(1) instanceof CircularString);
        CircularString cs = (CircularString) mc.getCurves().get(1);
        assertNull(cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(2, 1), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(2, 2), cs.getCoordinates().get(2));
        // WKT
        assertEquals("MULTICURVE ((5.0 5.0, 3.0 5.0, 3.0 3.0, 0.0 3.0), CIRCULARSTRING (0.0 0.0, 2.0 1.0, 2.0 2.0))",
                mc.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;MULTICURVE((5 5,3 5,3 3,0 3),CIRCULARSTRING(0 0,2 1,2 2))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiCurve);
        MultiCurve mc = (MultiCurve) geometry;
        assertFalse(mc.isEmpty());
        assertEquals("4326", mc.getSrid());
        assertEquals(Dimension.Two, mc.getDimension());
        assertEquals(2, mc.getCurves().size());
        // 0
        assertTrue(mc.getCurves().get(0) instanceof LineString);
        LineString line = (LineString) mc.getCurves().get(0);
        assertEquals("4326", line.getSrid());
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(5, 5), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 5), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(3, 3), line.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 3), line.getCoordinates().get(3));
        // 1
        assertTrue(mc.getCurves().get(1) instanceof CircularString);
        CircularString cs = (CircularString) mc.getCurves().get(1);
        assertEquals("4326", cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(2, 1), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(2, 2), cs.getCoordinates().get(2));
        // WKT
        assertEquals("SRID=4326;MULTICURVE ((5.0 5.0, 3.0 5.0, 3.0 3.0, 0.0 3.0), " +
                "CIRCULARSTRING (0.0 0.0, 2.0 1.0, 2.0 2.0))", mc.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "MULTICURVE M ((5 5 4,3 5 3,3 3 2,0 3 1),CIRCULARSTRING(0 0 1,2 1 2,2 2 3))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiCurve);
        MultiCurve mc = (MultiCurve) geometry;
        assertFalse(mc.isEmpty());
        assertNull(mc.getSrid());
        assertEquals(Dimension.TwoMeasured, mc.getDimension());
        assertEquals(2, mc.getCurves().size());
        // 0
        assertTrue(mc.getCurves().get(0) instanceof LineString);
        LineString line = (LineString) mc.getCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.TwoMeasured, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(5, 5, 4), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(3, 5, 3), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(3, 3, 2), line.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(0, 3, 1), line.getCoordinates().get(3));
        // 1
        assertTrue(mc.getCurves().get(1) instanceof CircularString);
        CircularString cs = (CircularString) mc.getCurves().get(1);
        assertNull(cs.getSrid());
        assertEquals(Dimension.TwoMeasured, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2DM(0, 0, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(2, 1, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(2, 2, 3), cs.getCoordinates().get(2));
        // WKT
        assertEquals("MULTICURVE M ((5.0 5.0 4.0, 3.0 5.0 3.0, 3.0 3.0 2.0, 0.0 3.0 1.0), " +
                "CIRCULARSTRING (0.0 0.0 1.0, 2.0 1.0 2.0, 2.0 2.0 3.0))", mc.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "MULTICURVE Z ((5 5 4,3 5 3,3 3 2,0 3 1),CIRCULARSTRING(0 0 1,2 1 2,2 2 3))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiCurve);
        MultiCurve mc = (MultiCurve) geometry;
        assertFalse(mc.isEmpty());
        assertNull(mc.getSrid());
        assertEquals(Dimension.Three, mc.getDimension());
        assertEquals(2, mc.getCurves().size());
        // 0
        assertTrue(mc.getCurves().get(0) instanceof LineString);
        LineString line = (LineString) mc.getCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.Three, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(5, 5, 4), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 5, 3), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(3, 3, 2), line.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 3, 1), line.getCoordinates().get(3));
        // 1
        assertTrue(mc.getCurves().get(1) instanceof CircularString);
        CircularString cs = (CircularString) mc.getCurves().get(1);
        assertNull(cs.getSrid());
        assertEquals(Dimension.Three, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(2, 1, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(2, 2, 3), cs.getCoordinates().get(2));
        // WKT
        assertEquals("MULTICURVE Z ((5.0 5.0 4.0, 3.0 5.0 3.0, 3.0 3.0 2.0, 0.0 3.0 1.0), " +
                "CIRCULARSTRING (0.0 0.0 1.0, 2.0 1.0 2.0, 2.0 2.0 3.0))", mc.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "MULTICURVE ZM ((5 5 4 6,3 5 3 7,3 3 2 8,0 3 1 9),CIRCULARSTRING(0 0 1 11,2 1 2 12,2 2 3 13))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiCurve);
        MultiCurve mc = (MultiCurve) geometry;
        assertFalse(mc.isEmpty());
        assertNull(mc.getSrid());
        assertEquals(Dimension.ThreeMeasured, mc.getDimension());
        assertEquals(2, mc.getCurves().size());
        // 0
        assertTrue(mc.getCurves().get(0) instanceof LineString);
        LineString line = (LineString) mc.getCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.ThreeMeasured, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(5, 5, 4, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(3, 5, 3, 7), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(3, 3, 2, 8), line.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 3, 1, 9), line.getCoordinates().get(3));
        // 1
        assertTrue(mc.getCurves().get(1) instanceof CircularString);
        CircularString cs = (CircularString) mc.getCurves().get(1);
        assertNull(cs.getSrid());
        assertEquals(Dimension.ThreeMeasured, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 1, 11), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(2, 1, 2, 12), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(2, 2, 3, 13), cs.getCoordinates().get(2));
        // WKT
        assertEquals("MULTICURVE ZM ((5.0 5.0 4.0 6.0, 3.0 5.0 3.0 7.0, 3.0 3.0 2.0 8.0, 0.0 3.0 1.0 9.0), " +
                "CIRCULARSTRING (0.0 0.0 1.0 11.0, 2.0 1.0 2.0 12.0, 2.0 2.0 3.0 13.0))", mc.toString());
    }
}
