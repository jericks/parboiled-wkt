package org.cugos.parboiledwkt;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.*;

public class MultiLineStringTest {

    @Test
    public void empty() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT()).run("MULTILINESTRING EMPTY");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertTrue(mls.getLineStrings().isEmpty());
        // WKT
        assertEquals("MULTILINESTRING EMPTY", mls.toString());
    }

    @Test
    public void twoDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("MULTILINESTRING((0 0,1 1,1 2),(2 3,3 2,5 4))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.Two, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        assertEquals(6, mls.getNumberOfCoordinates());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(1, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 2), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(2, 3), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 2), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(5, 4), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING ((0.0 0.0, 1.0 1.0, 1.0 2.0), (2.0 3.0, 3.0 2.0, 5.0 4.0))", mls.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("MULTILINESTRING M ((0 0 0,1 1 0,1 2 1),(2 3 1,3 2 1,5 4 1))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.TwoMeasured, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(0, 0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(1, 1, 0), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(1, 2, 1), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(2, 3, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(3, 2, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(5, 4, 1), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING M ((0.0 0.0 0.0, 1.0 1.0 0.0, 1.0 2.0 1.0), (2.0 3.0 1.0, 3.0 2.0 1.0, 5.0 4.0 1.0))", mls.toString());
    }

    @Test
    public void threeDimensional() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("MULTILINESTRING Z ((0 0 0,1 1 0,1 2 1),(2 3 1,3 2 1,5 4 1))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.Three, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(1, 1, 0), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 2, 1), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(2, 3, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 2, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(5, 4, 1), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING Z ((0.0 0.0 0.0, 1.0 1.0 0.0, 1.0 2.0 1.0), (2.0 3.0 1.0, 3.0 2.0 1.0, 5.0 4.0 1.0))", mls.toString());
    }

    @Test
    public void threeDimensionalWithoutZ() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("MULTILINESTRING((0 0 0,1 1 0,1 2 1),(2 3 1,3 2 1,5 4 1))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.Three, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(1, 1, 0), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 2, 1), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(2, 3, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 2, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(5, 4, 1), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING Z ((0.0 0.0 0.0, 1.0 1.0 0.0, 1.0 2.0 1.0), (2.0 3.0 1.0, 3.0 2.0 1.0, 5.0 4.0 1.0))", mls.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTParser parser = Parboiled.createParser(WKTParser.class);
        ParsingResult<Geometry> result = new ReportingParseRunner(parser.WKT())
                .run("MULTILINESTRING ZM ((0 0 0 7,1 1 0 8,1 2 1 9),(2 3 1 5,3 2 1 4,5 4 1 3))");
        Geometry geometry = result.resultValue;
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.ThreeMeasured, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 0, 7), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(1, 1, 0, 8), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(1, 2, 1, 9), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(2, 3, 1, 5), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(3, 2, 1, 4), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(5, 4, 1, 3), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING ZM ((0.0 0.0 0.0 7.0, 1.0 1.0 0.0 8.0, 1.0 2.0 1.0 9.0), " +
                "(2.0 3.0 1.0 5.0, 3.0 2.0 1.0 4.0, 5.0 4.0 1.0 3.0))", mls.toString());
    }
}
