package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class CurvePolygonTest {

    @Test
    public void empty() {
        String wkt = "CURVEPOLYGON EMPTY";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) geometry;
        assertTrue(cp.getOuterCurve().isEmpty());
        assertNull(cp.getSrid());
        assertEquals(Dimension.Two, cp.getDimension());
        // WKT
        assertEquals("CURVEPOLYGON EMPTY", cp.toString());
    }


    @Test
    public void twoDimensional() {
        String wkt = "CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) geometry;
        assertFalse(cp.getOuterCurve().isEmpty());
        assertEquals(1, cp.getInnerCurves().size());
        assertEquals(9, cp.getNumberOfCoordinates());
        assertNull(cp.getSrid());
        assertEquals(Dimension.Two, cp.getDimension());
        // Outer
        assertTrue(cp.getOuterCurve() instanceof CircularString);
        CircularString cs = (CircularString) cp.getOuterCurve();
        assertNull(cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(5, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(4, 0), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(4, 4), cs.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 4), cs.getCoordinates().get(3));
        assertEquals(Coordinate.create2D(0, 0), cs.getCoordinates().get(4));
        // Inner
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString line = (LineString) cp.getInnerCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 3), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(3, 1), line.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(1, 1), line.getCoordinates().get(3));
        // WKT
        assertEquals("CURVEPOLYGON (" +
                        "CIRCULARSTRING (0.0 0.0, 4.0 0.0, 4.0 4.0, 0.0 4.0, 0.0 0.0)), " +
                        "(1.0 1.0, 3.0 3.0, 3.0 1.0, 1.0 1.0))",
                cp.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) geometry;
        assertFalse(cp.getOuterCurve().isEmpty());
        assertEquals(1, cp.getInnerCurves().size());
        assertEquals("4326", cp.getSrid());
        assertEquals(Dimension.Two, cp.getDimension());
        // Outer
        assertTrue(cp.getOuterCurve() instanceof CircularString);
        CircularString cs = (CircularString) cp.getOuterCurve();
        assertEquals("4326", cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(5, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(4, 0), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(4, 4), cs.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 4), cs.getCoordinates().get(3));
        assertEquals(Coordinate.create2D(0, 0), cs.getCoordinates().get(4));
        // Inner
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString line = (LineString) cp.getInnerCurves().get(0);
        assertEquals("4326", line.getSrid());
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 3), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(3, 1), line.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(1, 1), line.getCoordinates().get(3));
        // WKT
        assertEquals("SRID=4326;CURVEPOLYGON (" +
                        "CIRCULARSTRING (0.0 0.0, 4.0 0.0, 4.0 4.0, 0.0 4.0, 0.0 0.0)), " +
                        "(1.0 1.0, 3.0 3.0, 3.0 1.0, 1.0 1.0))",
                cp.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "CURVEPOLYGON M (CIRCULARSTRING(0 0 1, 4 0 2, 4 4 3, 0 4 4, 0 0 1),(1 1 6, 3 3 7, 3 1 8, 1 1 6))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) geometry;
        assertFalse(cp.getOuterCurve().isEmpty());
        assertEquals(1, cp.getInnerCurves().size());
        assertNull(cp.getSrid());
        assertEquals(Dimension.TwoMeasured, cp.getDimension());
        // Outer
        assertTrue(cp.getOuterCurve() instanceof CircularString);
        CircularString cs = (CircularString) cp.getOuterCurve();
        assertNull(cs.getSrid());
        assertEquals(Dimension.TwoMeasured, cs.getDimension());
        assertEquals(5, cs.getCoordinates().size());
        assertEquals(Coordinate.create2DM(0, 0, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(4, 0, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(4, 4, 3), cs.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(0, 4, 4), cs.getCoordinates().get(3));
        assertEquals(Coordinate.create2DM(0, 0, 1), cs.getCoordinates().get(4));
        // Inner
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString line = (LineString) cp.getInnerCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.TwoMeasured, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(1, 1, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(3, 3, 7), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(3, 1, 8), line.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(1, 1, 6), line.getCoordinates().get(3));
        // WKT
        assertEquals("CURVEPOLYGON M (" +
                        "CIRCULARSTRING (0.0 0.0 1.0, 4.0 0.0 2.0, 4.0 4.0 3.0, 0.0 4.0 4.0, 0.0 0.0 1.0)), " +
                        "(1.0 1.0 6.0, 3.0 3.0 7.0, 3.0 1.0 8.0, 1.0 1.0 6.0))",
                cp.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "CURVEPOLYGON Z (CIRCULARSTRING(0 0 1, 4 0 2, 4 4 3, 0 4 4, 0 0 1),(1 1 6, 3 3 7, 3 1 8, 1 1 6))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) geometry;
        assertFalse(cp.getOuterCurve().isEmpty());
        assertEquals(1, cp.getInnerCurves().size());
        assertNull(cp.getSrid());
        assertEquals(Dimension.Three, cp.getDimension());
        // Outer
        assertTrue(cp.getOuterCurve() instanceof CircularString);
        CircularString cs = (CircularString) cp.getOuterCurve();
        assertNull(cs.getSrid());
        assertEquals(Dimension.Three, cs.getDimension());
        assertEquals(5, cs.getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(4, 0, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(4, 4, 3), cs.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 4, 4), cs.getCoordinates().get(3));
        assertEquals(Coordinate.create3D(0, 0, 1), cs.getCoordinates().get(4));
        // Inner
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString line = (LineString) cp.getInnerCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.Three, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(1, 1, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 3, 7), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(3, 1, 8), line.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(1, 1, 6), line.getCoordinates().get(3));
        // WKT
        assertEquals("CURVEPOLYGON Z (" +
                        "CIRCULARSTRING (0.0 0.0 1.0, 4.0 0.0 2.0, 4.0 4.0 3.0, 0.0 4.0 4.0, 0.0 0.0 1.0)), " +
                        "(1.0 1.0 6.0, 3.0 3.0 7.0, 3.0 1.0 8.0, 1.0 1.0 6.0))",
                cp.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "CURVEPOLYGON ZM (CIRCULARSTRING(0 0 1 2.3, 4 0 2 3.3, 4 4 3 4.3, 0 4 4 5.3, 0 0 1 2.3),(1 1 6 1.11, 3 3 7 2.22, 3 1 8 3.33, 1 1 6 1.11))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) geometry;
        assertFalse(cp.getOuterCurve().isEmpty());
        assertEquals(1, cp.getInnerCurves().size());
        assertNull(cp.getSrid());
        assertEquals(Dimension.ThreeMeasured, cp.getDimension());
        // Outer
        assertTrue(cp.getOuterCurve() instanceof CircularString);
        CircularString cs = (CircularString) cp.getOuterCurve();
        assertNull(cs.getSrid());
        assertEquals(Dimension.ThreeMeasured, cs.getDimension());
        assertEquals(5, cs.getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 1, 2.3), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(4, 0, 2, 3.3), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(4, 4, 3, 4.3), cs.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 4, 4, 5.3), cs.getCoordinates().get(3));
        assertEquals(Coordinate.create3DM(0, 0, 1, 2.3), cs.getCoordinates().get(4));
        // Inner
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString line = (LineString) cp.getInnerCurves().get(0);
        assertNull(line.getSrid());
        assertEquals(Dimension.ThreeMeasured, line.getDimension());
        assertEquals(4, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(1, 1, 6, 1.11), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(3, 3, 7, 2.22), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(3, 1, 8, 3.33), line.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(1, 1, 6, 1.11), line.getCoordinates().get(3));
        // WKT
        assertEquals("CURVEPOLYGON ZM (" +
                        "CIRCULARSTRING (0.0 0.0 1.0 2.3, 4.0 0.0 2.0 3.3, 4.0 4.0 3.0 4.3, 0.0 4.0 4.0 5.3, 0.0 0.0 1.0 2.3)), " +
                        "(1.0 1.0 6.0 1.11, 3.0 3.0 7.0 2.22, 3.0 1.0 8.0 3.33, 1.0 1.0 6.0 1.11))",
                cp.toString());
    }

}
