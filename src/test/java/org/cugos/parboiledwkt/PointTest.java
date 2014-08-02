package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

/**
 * A Test for parsing Points
 * @author Jared Erickson
 */
public class PointTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Two, point.getDimension());
        assertTrue(point.isEmpty());
        assertTrue(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Two);
        assertTrue(Double.isNaN(point.getCoordinate().getX()));
        assertTrue(Double.isNaN(point.getCoordinate().getY()));
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertNull(point.getSrid());
        assertEquals("POINT EMPTY", point.toString());
    }

    @Test
    public void twoDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT (1 2)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Two, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Two);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertNull(point.getSrid());
        assertEquals("POINT (1.0 2.0)", point.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT M (1 2 3)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.TwoMeasured, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.TwoMeasured);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(3, point.getCoordinate().getM(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertNull(point.getSrid());
        assertEquals("POINT M (1.0 2.0 3.0)", point.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT Z (1 2 3)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Three, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Three);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(3, point.getCoordinate().getZ(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertNull(point.getSrid());
        assertEquals("POINT Z (1.0 2.0 3.0)", point.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT ZM (1 2 3 4)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.ThreeMeasured, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.ThreeMeasured);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(3, point.getCoordinate().getZ(), 0.001);
        assertEquals(4, point.getCoordinate().getM(), 0.001);
        assertNull(point.getSrid());
        assertEquals("POINT ZM (1.0 2.0 3.0 4.0)", point.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("SRID=4326;POINT (1 2)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Two, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Two);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertEquals("4326", point.getSrid());
        assertEquals("SRID=4326;POINT (1.0 2.0)", point.toString());
    }

    @Test
    public void parseMultiple() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        // 1
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("POINT (1 2)");
        Geometry geometry = result.resultValue;
        assertEquals("POINT (1.0 2.0)", geometry.toString());
        // 2
        result = new ReportingParseRunner(parser.WKT()).run("POINT M (1 2 4.5)");
        geometry = result.resultValue;
        assertEquals("POINT M (1.0 2.0 4.5)", geometry.toString());
        // 3
        result = new ReportingParseRunner(parser.WKT()).run("POINT (-122.014487 46.982752)");
        geometry = result.resultValue;
        assertEquals("POINT (-122.014487 46.982752)", geometry.toString());
    }
}
