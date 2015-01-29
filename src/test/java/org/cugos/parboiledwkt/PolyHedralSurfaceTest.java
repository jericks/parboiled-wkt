package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class PolyHedralSurfaceTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POLYHEDRALSURFACE EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof PolyHedralSurface);
        PolyHedralSurface phs = (PolyHedralSurface) geometry;
        assertTrue(phs.getPolygons().isEmpty());
        // WKT
        assertEquals("POLYHEDRALSURFACE EMPTY", phs.toString());
        assertEquals(0, phs.getNumberOfCoordinates());
    }

    @Test
    public void twoDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("POLYHEDRALSURFACE (" +
                        "((40 40, 20 45, 45 30, 40 40))," +
                        "((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20))" +
                        ")");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof PolyHedralSurface);
        PolyHedralSurface phs = (PolyHedralSurface) geometry;
        assertNull(phs.getSrid());
        assertEquals(Dimension.Two, phs.getDimension());
        assertEquals(14, phs.getNumberOfCoordinates());
        assertEquals(2, phs.getPolygons().size());
        // 0
        Polygon polygon = phs.getPolygons().get(0);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.Two, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(4, polygon.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, polygon.getInnerLinearRings().size());
        LinearRing ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.Two, ring.getDimension());
        assertEquals(Coordinate.create2D(40, 40), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(20, 45), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(45, 30), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(40, 40), ring.getCoordinates().get(3));
        // 1
        polygon = phs.getPolygons().get(1);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.Two, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(1, polygon.getInnerLinearRings().size());
        ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.Two, ring.getDimension());
        assertEquals(6, ring.getCoordinates().size());
        assertEquals(Coordinate.create2D(20, 35), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(10, 30), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(10, 10), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(30, 5), ring.getCoordinates().get(3));
        assertEquals(Coordinate.create2D(45, 20), ring.getCoordinates().get(4));
        assertEquals(Coordinate.create2D(20, 35), ring.getCoordinates().get(5));
        ring = polygon.getInnerLinearRings().get(0);
        assertNull(ring.getSrid());
        assertEquals(Dimension.Two, ring.getDimension());
        assertEquals(4, ring.getCoordinates().size());
        assertEquals(Coordinate.create2D(30, 20), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(20, 15), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(20, 25), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(30, 20), ring.getCoordinates().get(3));
        // WKT
        assertEquals("POLYHEDRALSURFACE (" +
                "((40.0 40.0, 20.0 45.0, 45.0 30.0, 40.0 40.0)), " +
                "((20.0 35.0, 10.0 30.0, 10.0 10.0, 30.0 5.0, 45.0 20.0, 20.0 35.0), " +
                "(30.0 20.0, 20.0 15.0, 20.0 25.0, 30.0 20.0))" +
                ")", phs.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("SRID=4326;POLYHEDRALSURFACE (" +
                        "((40 40, 20 45, 45 30, 40 40))," +
                        "((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20))" +
                        ")");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof PolyHedralSurface);
        PolyHedralSurface phs = (PolyHedralSurface) geometry;
        assertEquals("4326", phs.getSrid());
        assertEquals(Dimension.Two, phs.getDimension());
        assertEquals(2, phs.getPolygons().size());
        // 0
        Polygon polygon = phs.getPolygons().get(0);
        assertEquals("4326", polygon.getSrid());
        assertEquals(Dimension.Two, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(4, polygon.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, polygon.getInnerLinearRings().size());
        LinearRing ring = polygon.getOuterLinearRing();
        assertEquals("4326", ring.getSrid());
        assertEquals(Dimension.Two, ring.getDimension());
        assertEquals(Coordinate.create2D(40, 40), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(20, 45), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(45, 30), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(40, 40), ring.getCoordinates().get(3));
        // 1
        polygon = phs.getPolygons().get(1);
        assertEquals("4326", polygon.getSrid());
        assertEquals(Dimension.Two, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(1, polygon.getInnerLinearRings().size());
        ring = polygon.getOuterLinearRing();
        assertEquals("4326", ring.getSrid());
        assertEquals(Dimension.Two, ring.getDimension());
        assertEquals(6, ring.getCoordinates().size());
        assertEquals(Coordinate.create2D(20, 35), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(10, 30), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(10, 10), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(30, 5), ring.getCoordinates().get(3));
        assertEquals(Coordinate.create2D(45, 20), ring.getCoordinates().get(4));
        assertEquals(Coordinate.create2D(20, 35), ring.getCoordinates().get(5));
        ring = polygon.getInnerLinearRings().get(0);
        assertEquals("4326", ring.getSrid());
        assertEquals(Dimension.Two, ring.getDimension());
        assertEquals(4, ring.getCoordinates().size());
        assertEquals(Coordinate.create2D(30, 20), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(20, 15), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(20, 25), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2D(30, 20), ring.getCoordinates().get(3));
        // WKT
        assertEquals("SRID=4326;POLYHEDRALSURFACE (" +
                "((40.0 40.0, 20.0 45.0, 45.0 30.0, 40.0 40.0)), " +
                "((20.0 35.0, 10.0 30.0, 10.0 10.0, 30.0 5.0, 45.0 20.0, 20.0 35.0), " +
                "(30.0 20.0, 20.0 15.0, 20.0 25.0, 30.0 20.0))" +
                ")", phs.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("POLYHEDRALSURFACE M (" +
                        "((40 40 1, 20 45 2, 45 30 3, 40 40 1))," +
                        "((20 35 1, 10 30 2, 10 10 3, 30 5 4, 45 20 5, 20 35 1),(30 20 1, 20 15 2, 20 25 3, 30 20 1))" +
                        ")");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof PolyHedralSurface);
        PolyHedralSurface phs = (PolyHedralSurface) geometry;
        assertNull(phs.getSrid());
        assertEquals(Dimension.TwoMeasured, phs.getDimension());
        assertEquals(2, phs.getPolygons().size());
        // 0
        Polygon polygon = phs.getPolygons().get(0);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.TwoMeasured, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(4, polygon.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, polygon.getInnerLinearRings().size());
        LinearRing ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.TwoMeasured, ring.getDimension());
        assertEquals(Coordinate.create2DM(40, 40, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(20, 45, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(45, 30, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(40, 40, 1), ring.getCoordinates().get(3));
        // 1
        polygon = phs.getPolygons().get(1);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.TwoMeasured, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(1, polygon.getInnerLinearRings().size());
        ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.TwoMeasured, ring.getDimension());
        assertEquals(6, ring.getCoordinates().size());
        assertEquals(Coordinate.create2DM(20, 35, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(10, 30, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(10, 10, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(30, 5, 4), ring.getCoordinates().get(3));
        assertEquals(Coordinate.create2DM(45, 20, 5), ring.getCoordinates().get(4));
        assertEquals(Coordinate.create2DM(20, 35, 1), ring.getCoordinates().get(5));
        ring = polygon.getInnerLinearRings().get(0);
        assertNull(ring.getSrid());
        assertEquals(Dimension.TwoMeasured, ring.getDimension());
        assertEquals(4, ring.getCoordinates().size());
        assertEquals(Coordinate.create2DM(30, 20, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(20, 15, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(20, 25, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(30, 20, 1), ring.getCoordinates().get(3));
        // WKT
        assertEquals("POLYHEDRALSURFACE M (" +
                "((40.0 40.0 1.0, 20.0 45.0 2.0, 45.0 30.0 3.0, 40.0 40.0 1.0)), " +
                "((20.0 35.0 1.0, 10.0 30.0 2.0, 10.0 10.0 3.0, 30.0 5.0 4.0, 45.0 20.0 5.0, 20.0 35.0 1.0), " +
                "(30.0 20.0 1.0, 20.0 15.0 2.0, 20.0 25.0 3.0, 30.0 20.0 1.0))" +
                ")", phs.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("POLYHEDRALSURFACE Z (" +
                        "((40 40 1, 20 45 2, 45 30 3, 40 40 1))," +
                        "((20 35 1, 10 30 2, 10 10 3, 30 5 4, 45 20 5, 20 35 1),(30 20 1, 20 15 2, 20 25 3, 30 20 1))" +
                        ")");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof PolyHedralSurface);
        PolyHedralSurface phs = (PolyHedralSurface) geometry;
        assertNull(phs.getSrid());
        assertEquals(Dimension.Three, phs.getDimension());
        assertEquals(2, phs.getPolygons().size());
        // 0
        Polygon polygon = phs.getPolygons().get(0);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.Three, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(4, polygon.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, polygon.getInnerLinearRings().size());
        LinearRing ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.Three, ring.getDimension());
        assertEquals(Coordinate.create3D(40, 40, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(20, 45, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(45, 30, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(40, 40, 1), ring.getCoordinates().get(3));
        // 1
        polygon = phs.getPolygons().get(1);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.Three, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(1, polygon.getInnerLinearRings().size());
        ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.Three, ring.getDimension());
        assertEquals(6, ring.getCoordinates().size());
        assertEquals(Coordinate.create3D(20, 35, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(10, 30, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(10, 10, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(30, 5, 4), ring.getCoordinates().get(3));
        assertEquals(Coordinate.create3D(45, 20, 5), ring.getCoordinates().get(4));
        assertEquals(Coordinate.create3D(20, 35, 1), ring.getCoordinates().get(5));
        ring = polygon.getInnerLinearRings().get(0);
        assertNull(ring.getSrid());
        assertEquals(Dimension.Three, ring.getDimension());
        assertEquals(4, ring.getCoordinates().size());
        assertEquals(Coordinate.create3D(30, 20, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(20, 15, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(20, 25, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create3D(30, 20, 1), ring.getCoordinates().get(3));
        // WKT
        assertEquals("POLYHEDRALSURFACE Z (" +
                "((40.0 40.0 1.0, 20.0 45.0 2.0, 45.0 30.0 3.0, 40.0 40.0 1.0)), " +
                "((20.0 35.0 1.0, 10.0 30.0 2.0, 10.0 10.0 3.0, 30.0 5.0 4.0, 45.0 20.0 5.0, 20.0 35.0 1.0), " +
                "(30.0 20.0 1.0, 20.0 15.0 2.0, 20.0 25.0 3.0, 30.0 20.0 1.0))" +
                ")", phs.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("POLYHEDRALSURFACE ZM (" +
                        "((40 40 1 1, 20 45 2 2, 45 30 3 3, 40 40 1 1))," +
                        "((20 35 1 1, 10 30 2 2, 10 10 3 3, 30 5 4 4, 45 20 5 5, 20 35 1 1)," +
                        "(30 20 1 1, 20 15 2 2, 20 25 3 3, 30 20 1 1)))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof PolyHedralSurface);
        PolyHedralSurface phs = (PolyHedralSurface) geometry;
        assertNull(phs.getSrid());
        assertEquals(Dimension.ThreeMeasured, phs.getDimension());
        assertEquals(2, phs.getPolygons().size());
        // 0
        Polygon polygon = phs.getPolygons().get(0);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.ThreeMeasured, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(4, polygon.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, polygon.getInnerLinearRings().size());
        LinearRing ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.ThreeMeasured, ring.getDimension());
        assertEquals(Coordinate.create3DM(40, 40, 1, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(20, 45, 2, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(45, 30, 3, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(40, 40, 1, 1), ring.getCoordinates().get(3));
        // 1
        polygon = phs.getPolygons().get(1);
        assertNull(polygon.getSrid());
        assertEquals(Dimension.ThreeMeasured, polygon.getDimension());
        assertNotNull(polygon.getOuterLinearRing());
        assertEquals(1, polygon.getInnerLinearRings().size());
        ring = polygon.getOuterLinearRing();
        assertNull(ring.getSrid());
        assertEquals(Dimension.ThreeMeasured, ring.getDimension());
        assertEquals(6, ring.getCoordinates().size());
        assertEquals(Coordinate.create3DM(20, 35, 1, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(10, 30, 2, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(10, 10, 3, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(30, 5, 4, 4), ring.getCoordinates().get(3));
        assertEquals(Coordinate.create3DM(45, 20, 5, 5), ring.getCoordinates().get(4));
        assertEquals(Coordinate.create3DM(20, 35, 1, 1), ring.getCoordinates().get(5));
        ring = polygon.getInnerLinearRings().get(0);
        assertNull(ring.getSrid());
        assertEquals(Dimension.ThreeMeasured, ring.getDimension());
        assertEquals(4, ring.getCoordinates().size());
        assertEquals(Coordinate.create3DM(30, 20, 1, 1), ring.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(20, 15, 2, 2), ring.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(20, 25, 3, 3), ring.getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(30, 20, 1, 1), ring.getCoordinates().get(3));
        // WKT
        assertEquals("POLYHEDRALSURFACE ZM (" +
                "((40.0 40.0 1.0 1.0, 20.0 45.0 2.0 2.0, 45.0 30.0 3.0 3.0, 40.0 40.0 1.0 1.0)), " +
                "((20.0 35.0 1.0 1.0, 10.0 30.0 2.0 2.0, 10.0 10.0 3.0 3.0, 30.0 5.0 4.0 4.0, 45.0 20.0 5.0 5.0, 20.0 35.0 1.0 1.0), " +
                "(30.0 20.0 1.0 1.0, 20.0 15.0 2.0 2.0, 20.0 25.0 3.0 3.0, 30.0 20.0 1.0 1.0))" +
                ")", phs.toString());
    }
}
