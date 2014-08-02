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
public class MultiPointTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTIPOINT EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertTrue(mp.getPoints().isEmpty());
        // WKT
        assertEquals("MULTIPOINT EMPTY", mp.toString());
    }

    @Test
    public void twoDimensional1() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTIPOINT (10 40, 40 30, 20 20, 30 10)");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("MULTIPOINT (10.0 40.0, 40.0 30.0, 20.0 20.0, 30.0 10.0)", mp.toString());
    }

    @Test
    public void twoDimensional2() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("MULTIPOINT (10.0 40.0, 40.0 30.0, 20.0 20.0, 30.0 10.0)", mp.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTIPOINT M ((10 40 1), (40 30 2), (20 20 3), (30 10 4))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertEquals(1, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertEquals(2, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertEquals(3, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertEquals(4, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("MULTIPOINT M (10.0 40.0 1.0, 40.0 30.0 2.0, 20.0 20.0 3.0, 30.0 10.0 4.0)", mp.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTIPOINT Z ((10 40 1), (40 30 2), (20 20 3), (30 10 4))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertEquals(1, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertEquals(2, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertEquals(3, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertEquals(4, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // WKT
        assertEquals("MULTIPOINT Z (10.0 40.0 1.0, 40.0 30.0 2.0, 20.0 20.0 3.0, 30.0 10.0 4.0)", mp.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTIPOINT ZM ((10 40 1 4), (40 30 2 3), (20 20 3 2), (30 10 4 1))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertEquals(1, c.getZ(), 0.01);
        assertEquals(4, c.getM(), 0.01);
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertEquals(2, c.getZ(), 0.01);
        assertEquals(3, c.getM(), 0.01);
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertEquals(3, c.getZ(), 0.01);
        assertEquals(2, c.getM(), 0.01);
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertEquals(4, c.getZ(), 0.01);
        assertEquals(1, c.getM(), 0.01);
        // WKT
        assertEquals("MULTIPOINT ZM (10.0 40.0 1.0 4.0, 40.0 30.0 2.0 3.0, 20.0 20.0 3.0 2.0, 30.0 10.0 4.0 1.0)", mp.toString());
    }

}
