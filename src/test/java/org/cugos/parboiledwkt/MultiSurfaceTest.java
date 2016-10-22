package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class MultiSurfaceTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTISURFACE EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiSurface);
        MultiSurface ms = (MultiSurface) geometry;
        assertTrue(ms.isEmpty());
        assertNull(ms.getSrid());
        assertEquals(Dimension.Two, ms.getDimension());
        // WKT
        assertEquals("MULTISURFACE EMPTY", ms.toString());
    }

    @Test
    public void twoDimensional() {
        String wkt = "MULTISURFACE(CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1)),((10 10, 14 12, 11 10, 10 10),(11 11, 11.5 11, 11 11.5, 11 11)))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiSurface);
        MultiSurface ms = (MultiSurface) geometry;
        assertFalse(ms.isEmpty());
        assertNull(ms.getSrid());
        assertEquals(Dimension.Two, ms.getDimension());
        assertEquals(17, ms.getNumberOfCoordinates());
        assertEquals(2, ms.getSurfaces().size());
        // 0
        assertTrue(ms.getSurfaces().get(0) instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) ms.getSurfaces().get(0);
        assertNull(cp.getSrid());
        assertEquals(Dimension.Two, cp.getDimension());
        // 0 Outer
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
        // 0 Inner
        assertEquals(1, cp.getInnerCurves().size());
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString ls = (LineString) cp.getInnerCurves().get(0);
        assertNull(ls.getSrid());
        assertEquals(Dimension.Two, ls.getDimension());
        assertEquals(4, ls.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 1), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 3), ls.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(3, 1), ls.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(1, 1), ls.getCoordinates().get(3));
        // 1
        assertTrue(ms.getSurfaces().get(1) instanceof Polygon);
        Polygon p = (Polygon) ms.getSurfaces().get(1);
        assertNull(p.getSrid());
        assertEquals(Dimension.Two, p.getDimension());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(1, p.getInnerLinearRings().size());
        // 1 Outer
        LinearRing or = p.getOuterLinearRing();
        assertNull(or.getSrid());
        assertEquals(Dimension.Two, or.getDimension());
        assertEquals(4, or.getCoordinates().size());
        assertEquals(Coordinate.create2D(10, 10), or.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(14, 12), or.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(11, 10), or.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(10, 10), or.getCoordinates().get(3));
        // 1 Inner
        LinearRing ir = p.getInnerLinearRings().get(0);
        assertNull(ir.getSrid());
        assertEquals(Dimension.Two, ir.getDimension());
        assertEquals(4, ir.getCoordinates().size());
        assertEquals(Coordinate.create2D(11, 11), ir.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(11.5, 11), ir.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(11, 11.5), ir.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(11, 11), ir.getCoordinates().get(3));
        // WKT
        assertEquals("MULTISURFACE (CURVEPOLYGON (CIRCULARSTRING (0.0 0.0, 4.0 0.0, 4.0 4.0, 0.0 4.0, 0.0 0.0), " +
                        "(1.0 1.0, 3.0 3.0, 3.0 1.0, 1.0 1.0)), " +
                        "((10.0 10.0, 14.0 12.0, 11.0 10.0, 10.0 10.0), (11.0 11.0, 11.5 11.0, 11.0 11.5, 11.0 11.0)))",
                ms.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;MULTISURFACE(CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1)),((10 10, 14 12, 11 10, 10 10),(11 11, 11.5 11, 11 11.5, 11 11)))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiSurface);
        MultiSurface ms = (MultiSurface) geometry;
        assertFalse(ms.isEmpty());
        assertEquals("4326", ms.getSrid());
        assertEquals(Dimension.Two, ms.getDimension());
        assertEquals(2, ms.getSurfaces().size());
        // 0
        assertTrue(ms.getSurfaces().get(0) instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) ms.getSurfaces().get(0);
        assertEquals("4326", cp.getSrid());
        assertEquals(Dimension.Two, cp.getDimension());
        // 0 Outer
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
        // 0 Inner
        assertEquals(1, cp.getInnerCurves().size());
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString ls = (LineString) cp.getInnerCurves().get(0);
        assertEquals("4326", ls.getSrid());
        assertEquals(Dimension.Two, ls.getDimension());
        assertEquals(4, ls.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 1), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 3), ls.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(3, 1), ls.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(1, 1), ls.getCoordinates().get(3));
        // 1
        assertTrue(ms.getSurfaces().get(1) instanceof Polygon);
        Polygon p = (Polygon) ms.getSurfaces().get(1);
        assertEquals("4326", p.getSrid());
        assertEquals(Dimension.Two, p.getDimension());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(1, p.getInnerLinearRings().size());
        // 1 Outer
        LinearRing or = p.getOuterLinearRing();
        assertEquals("4326", or.getSrid());
        assertEquals(Dimension.Two, or.getDimension());
        assertEquals(4, or.getCoordinates().size());
        assertEquals(Coordinate.create2D(10, 10), or.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(14, 12), or.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(11, 10), or.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(10, 10), or.getCoordinates().get(3));
        // 1 Inner
        LinearRing ir = p.getInnerLinearRings().get(0);
        assertEquals("4326", ir.getSrid());
        assertEquals(Dimension.Two, ir.getDimension());
        assertEquals(4, ir.getCoordinates().size());
        assertEquals(Coordinate.create2D(11, 11), ir.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(11.5, 11), ir.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(11, 11.5), ir.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(11, 11), ir.getCoordinates().get(3));
        // WKT
        assertEquals("SRID=4326;MULTISURFACE (CURVEPOLYGON (CIRCULARSTRING (0.0 0.0, 4.0 0.0, 4.0 4.0, 0.0 4.0, 0.0 0.0), " +
                        "(1.0 1.0, 3.0 3.0, 3.0 1.0, 1.0 1.0)), " +
                        "((10.0 10.0, 14.0 12.0, 11.0 10.0, 10.0 10.0), (11.0 11.0, 11.5 11.0, 11.0 11.5, 11.0 11.0)))",
                ms.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "MULTISURFACE M (CURVEPOLYGON(CIRCULARSTRING(0 0 1, 4 0 2, 4 4 3, 0 4 4, 0 0 1),(1 1 1, 3 3 2, 3 1 3, 1 1 1))," +
                "((10 10 20, 14 12 21, 11 10 22, 10 10 20),(11 11 30, 11.5 11 31, 11 11.5 32, 11 11 30)))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiSurface);
        MultiSurface ms = (MultiSurface) geometry;
        assertFalse(ms.isEmpty());
        assertNull(ms.getSrid());
        assertEquals(Dimension.TwoMeasured, ms.getDimension());
        assertEquals(2, ms.getSurfaces().size());
        // 0
        assertTrue(ms.getSurfaces().get(0) instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) ms.getSurfaces().get(0);
        assertNull(cp.getSrid());
        assertEquals(Dimension.TwoMeasured, cp.getDimension());
        // 0 Outer
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
        // 0 Inner
        assertEquals(1, cp.getInnerCurves().size());
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString ls = (LineString) cp.getInnerCurves().get(0);
        assertNull(ls.getSrid());
        assertEquals(Dimension.TwoMeasured, ls.getDimension());
        assertEquals(4, ls.getCoordinates().size());
        assertEquals(Coordinate.create2DM(1, 1, 1), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(3, 3, 2), ls.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(3, 1, 3), ls.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(1, 1, 1), ls.getCoordinates().get(3));
        // 1
        assertTrue(ms.getSurfaces().get(1) instanceof Polygon);
        Polygon p = (Polygon) ms.getSurfaces().get(1);
        assertNull(p.getSrid());
        assertEquals(Dimension.TwoMeasured, p.getDimension());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(1, p.getInnerLinearRings().size());
        // 1 Outer
        LinearRing or = p.getOuterLinearRing();
        assertNull(or.getSrid());
        assertEquals(Dimension.TwoMeasured, or.getDimension());
        assertEquals(4, or.getCoordinates().size());
        assertEquals(Coordinate.create2DM(10, 10, 20), or.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(14, 12, 21), or.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(11, 10, 22), or.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(10, 10, 20), or.getCoordinates().get(3));
        // 1 Inner
        LinearRing ir = p.getInnerLinearRings().get(0);
        assertNull(ir.getSrid());
        assertEquals(Dimension.TwoMeasured, ir.getDimension());
        assertEquals(4, ir.getCoordinates().size());
        assertEquals(Coordinate.create2DM(11, 11, 30), ir.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(11.5, 11, 31), ir.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(11, 11.5, 32), ir.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(11, 11, 30), ir.getCoordinates().get(3));
        // WKT
        assertEquals("MULTISURFACE M (CURVEPOLYGON (CIRCULARSTRING (0.0 0.0 1.0, 4.0 0.0 2.0, 4.0 4.0 3.0, 0.0 4.0 4.0, 0.0 0.0 1.0), " +
                        "(1.0 1.0 1.0, 3.0 3.0 2.0, 3.0 1.0 3.0, 1.0 1.0 1.0)), " +
                        "((10.0 10.0 20.0, 14.0 12.0 21.0, 11.0 10.0 22.0, 10.0 10.0 20.0), " +
                        "(11.0 11.0 30.0, 11.5 11.0 31.0, 11.0 11.5 32.0, 11.0 11.0 30.0)))",
                ms.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "MULTISURFACE Z (CURVEPOLYGON(CIRCULARSTRING(0 0 1, 4 0 2, 4 4 3, 0 4 4, 0 0 1),(1 1 1, 3 3 2, 3 1 3, 1 1 1))," +
                "((10 10 20, 14 12 21, 11 10 22, 10 10 20),(11 11 30, 11.5 11 31, 11 11.5 32, 11 11 30)))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiSurface);
        MultiSurface ms = (MultiSurface) geometry;
        assertFalse(ms.isEmpty());
        assertNull(ms.getSrid());
        assertEquals(Dimension.Three, ms.getDimension());
        assertEquals(2, ms.getSurfaces().size());
        // 0
        assertTrue(ms.getSurfaces().get(0) instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) ms.getSurfaces().get(0);
        assertNull(cp.getSrid());
        assertEquals(Dimension.Three, cp.getDimension());
        // 0 Outer
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
        // 0 Inner
        assertEquals(1, cp.getInnerCurves().size());
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString ls = (LineString) cp.getInnerCurves().get(0);
        assertNull(ls.getSrid());
        assertEquals(Dimension.Three, ls.getDimension());
        assertEquals(4, ls.getCoordinates().size());
        assertEquals(Coordinate.create3D(1, 1, 1), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 3, 2), ls.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(3, 1, 3), ls.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(1, 1, 1), ls.getCoordinates().get(3));
        // 1
        assertTrue(ms.getSurfaces().get(1) instanceof Polygon);
        Polygon p = (Polygon) ms.getSurfaces().get(1);
        assertNull(p.getSrid());
        assertEquals(Dimension.Three, p.getDimension());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(1, p.getInnerLinearRings().size());
        // 1 Outer
        LinearRing or = p.getOuterLinearRing();
        assertNull(or.getSrid());
        assertEquals(Dimension.Three, or.getDimension());
        assertEquals(4, or.getCoordinates().size());
        assertEquals(Coordinate.create3D(10, 10, 20), or.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(14, 12, 21), or.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(11, 10, 22), or.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(10, 10, 20), or.getCoordinates().get(3));
        // 1 Inner
        LinearRing ir = p.getInnerLinearRings().get(0);
        assertNull(ir.getSrid());
        assertEquals(Dimension.Three, ir.getDimension());
        assertEquals(4, ir.getCoordinates().size());
        assertEquals(Coordinate.create3D(11, 11, 30), ir.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(11.5, 11, 31), ir.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(11, 11.5, 32), ir.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(11, 11, 30), ir.getCoordinates().get(3));
        // WKT
        assertEquals("MULTISURFACE Z (CURVEPOLYGON (CIRCULARSTRING (0.0 0.0 1.0, 4.0 0.0 2.0, 4.0 4.0 3.0, 0.0 4.0 4.0, 0.0 0.0 1.0), " +
                        "(1.0 1.0 1.0, 3.0 3.0 2.0, 3.0 1.0 3.0, 1.0 1.0 1.0)), " +
                        "((10.0 10.0 20.0, 14.0 12.0 21.0, 11.0 10.0 22.0, 10.0 10.0 20.0), " +
                        "(11.0 11.0 30.0, 11.5 11.0 31.0, 11.0 11.5 32.0, 11.0 11.0 30.0)))",
                ms.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "MULTISURFACE ZM (CURVEPOLYGON(CIRCULARSTRING(0 0 1 1, 4 0 2 2, 4 4 3 3, 0 4 4 4, 0 0 1 1),(1 1 1 1, 3 3 2 2, 3 1 3 3, 1 1 1 1))," +
                "((10 10 20 20, 14 12 21 21, 11 10 22 22, 10 10 20 20),(11 11 30 30, 11.5 11 31 31, 11 11.5 32 32, 11 11 30 30)))";
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run(wkt);
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiSurface);
        MultiSurface ms = (MultiSurface) geometry;
        assertFalse(ms.isEmpty());
        assertNull(ms.getSrid());
        assertEquals(Dimension.ThreeMeasured, ms.getDimension());
        assertEquals(2, ms.getSurfaces().size());
        // 0
        assertTrue(ms.getSurfaces().get(0) instanceof CurvePolygon);
        CurvePolygon cp = (CurvePolygon) ms.getSurfaces().get(0);
        assertNull(cp.getSrid());
        assertEquals(Dimension.ThreeMeasured, cp.getDimension());
        // 0 Outer
        assertTrue(cp.getOuterCurve() instanceof CircularString);
        CircularString cs = (CircularString) cp.getOuterCurve();
        assertNull(cs.getSrid());
        assertEquals(Dimension.ThreeMeasured, cs.getDimension());
        assertEquals(5, cs.getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 1, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(4, 0, 2, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(4, 4, 3, 3), cs.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 4, 4, 4), cs.getCoordinates().get(3));
        assertEquals(Coordinate.create3DM(0, 0, 1, 1), cs.getCoordinates().get(4));
        // 0 Inner
        assertEquals(1, cp.getInnerCurves().size());
        assertTrue(cp.getInnerCurves().get(0) instanceof LineString);
        LineString ls = (LineString) cp.getInnerCurves().get(0);
        assertNull(ls.getSrid());
        assertEquals(Dimension.ThreeMeasured, ls.getDimension());
        assertEquals(4, ls.getCoordinates().size());
        assertEquals(Coordinate.create3DM(1, 1, 1, 1), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(3, 3, 2, 2), ls.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(3, 1, 3, 3), ls.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(1, 1, 1, 1), ls.getCoordinates().get(3));
        // 1
        assertTrue(ms.getSurfaces().get(1) instanceof Polygon);
        Polygon p = (Polygon) ms.getSurfaces().get(1);
        assertNull(p.getSrid());
        assertEquals(Dimension.ThreeMeasured, p.getDimension());
        assertNotNull(p.getOuterLinearRing());
        assertEquals(1, p.getInnerLinearRings().size());
        // 1 Outer
        LinearRing or = p.getOuterLinearRing();
        assertNull(or.getSrid());
        assertEquals(Dimension.ThreeMeasured, or.getDimension());
        assertEquals(4, or.getCoordinates().size());
        assertEquals(Coordinate.create3DM(10, 10, 20, 20), or.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(14, 12, 21, 21), or.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(11, 10, 22, 22), or.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(10, 10, 20, 20), or.getCoordinates().get(3));
        // 1 Inner
        LinearRing ir = p.getInnerLinearRings().get(0);
        assertNull(ir.getSrid());
        assertEquals(Dimension.ThreeMeasured, ir.getDimension());
        assertEquals(4, ir.getCoordinates().size());
        assertEquals(Coordinate.create3DM(11, 11, 30, 30), ir.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(11.5, 11, 31, 31), ir.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(11, 11.5, 32, 32), ir.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(11, 11, 30, 30), ir.getCoordinates().get(3));
        // WKT
        assertEquals("MULTISURFACE ZM (CURVEPOLYGON (CIRCULARSTRING (0.0 0.0 1.0 1.0, 4.0 0.0 2.0 2.0, 4.0 4.0 3.0 3.0, 0.0 4.0 4.0 4.0, 0.0 0.0 1.0 1.0), " +
                        "(1.0 1.0 1.0 1.0, 3.0 3.0 2.0 2.0, 3.0 1.0 3.0 3.0, 1.0 1.0 1.0 1.0)), " +
                        "((10.0 10.0 20.0 20.0, 14.0 12.0 21.0 21.0, 11.0 10.0 22.0 22.0, 10.0 10.0 20.0 20.0), " +
                        "(11.0 11.0 30.0 30.0, 11.5 11.0 31.0 31.0, 11.0 11.5 32.0 32.0, 11.0 11.0 30.0 30.0)))",
                ms.toString());
    }

    @Test
    public void readWriteRead() {
        WKTReader reader =  new WKTReader();
        WKTWriter writer = new WKTWriter();
        String[] wkts = {
                "MULTISURFACE ZM (CURVEPOLYGON (CIRCULARSTRING (0.0 0.0 1.0 1.0, 4.0 0.0 2.0 2.0, 4.0 4.0 3.0 3.0, 0.0 4.0 4.0 4.0, 0.0 0.0 1.0 1.0), " +
                        "(1.0 1.0 1.0 1.0, 3.0 3.0 2.0 2.0, 3.0 1.0 3.0 3.0, 1.0 1.0 1.0 1.0)), " +
                        "((10.0 10.0 20.0 20.0, 14.0 12.0 21.0 21.0, 11.0 10.0 22.0 22.0, 10.0 10.0 20.0 20.0), " +
                        "(11.0 11.0 30.0 30.0, 11.5 11.0 31.0 31.0, 11.0 11.5 32.0 32.0, 11.0 11.0 30.0 30.0)))"
        };
        for(String wkt : wkts) {
            Geometry g = reader.read(wkt);
            assertNotNull(g);
            String newWkt = writer.write(g);
            assertEquals(wkt, newWkt);
        }
    }
}

