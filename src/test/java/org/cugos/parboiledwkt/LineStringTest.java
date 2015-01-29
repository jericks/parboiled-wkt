package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

/**
 * A Test for parsing LineStrings
 * @author Jared Erickson
 */
public class LineStringTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("LINESTRING EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof LineString);
        LineString line = (LineString) geometry;
        assertNull(line.getSrid());
        assertTrue(line.getCoordinates().isEmpty());
        assertEquals("LINESTRING EMPTY", line.toString());
    }

    @Test
    public void twoDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("LINESTRING (101 234,345 567)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof LineString);
        LineString line = (LineString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getNumberOfCoordinates());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(101, c.getX(), 0.01);
        assertEquals(234, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(345, c.getX(), 0.01);
        assertEquals(567, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("LINESTRING (101.0 234.0, 345.0 567.0)", line.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("SRID=4326;LINESTRING (101 234,345 567)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof LineString);
        LineString line = (LineString) geometry;
        assertEquals(2, line.getCoordinates().size());
        assertEquals("4326", line.getSrid());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(101, c.getX(), 0.01);
        assertEquals(234, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(345, c.getX(), 0.01);
        assertEquals(567, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("SRID=4326;LINESTRING (101.0 234.0, 345.0 567.0)", line.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("LINESTRING M (101 234 5,345 567 6)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof LineString);
        LineString line = (LineString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(101, c.getX(), 0.01);
        assertEquals(234, c.getY(), 0.01);
        assertEquals(5, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(345, c.getX(), 0.01);
        assertEquals(567, c.getY(), 0.01);
        assertEquals(6, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("LINESTRING M (101.0 234.0 5.0, 345.0 567.0 6.0)", line.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("LINESTRING Z (101.1 234.2 5.4, 345.6 567.7 6.8)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof LineString);
        LineString line = (LineString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(101.1, c.getX(), 0.01);
        assertEquals(234.2, c.getY(), 0.01);
        assertEquals(5.4, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(345.6, c.getX(), 0.01);
        assertEquals(567.7, c.getY(), 0.01);
        assertEquals(6.8, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // WKT
        assertEquals("LINESTRING Z (101.1 234.2 5.4, 345.6 567.7 6.8)", line.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("LINESTRING ZM (101.1 234.2 5.4 1.2, 345.6 567.7 6.8 99.2)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof LineString);
        LineString line = (LineString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(101.1, c.getX(), 0.01);
        assertEquals(234.2, c.getY(), 0.01);
        assertEquals(5.4, c.getZ(), 0.01);
        assertEquals(1.2, c.getM(), 0.01);
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(345.6, c.getX(), 0.01);
        assertEquals(567.7, c.getY(), 0.01);
        assertEquals(6.8, c.getZ(), 0.01);
        assertEquals(99.2, c.getM(), 0.01);
        // WKT
        assertEquals("LINESTRING ZM (101.1 234.2 5.4 1.2, 345.6 567.7 6.8 99.2)", line.toString());
    }

}
