package org.cugos.parboiledwkt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * The WKTWriter Unit Test
 *
 * @author Jared Erickson
 */
public class WKTWriterTest {

    @Test
    public void point() {
        WKTWriter writer = new WKTWriter();
        Point point = new Point(Coordinate.create2D(1, 2), Dimension.Two);
        assertEquals("POINT (1.0 2.0)", writer.write(point));
    }

    @Test
    public void lineString() {
        WKTWriter writer = new WKTWriter();
        LineString line = new LineString(Arrays.asList(
                Coordinate.create3D(1, 1, 10),
                Coordinate.create3D(5, 5, 15),
                Coordinate.create3D(10, 10, 20)
        ), Dimension.Three);
        assertEquals("LINESTRING Z (1.0 1.0 10.0, 5.0 5.0 15.0, 10.0 10.0 20.0)", writer.write(line));
    }

    @Test
    public void polygon() {
        WKTWriter writer = new WKTWriter();
        Polygon polygon = new Polygon(
                new LinearRing(Arrays.asList(
                    Coordinate.create2D(1191095.0091707027, 657195.1523189925),
                    Coordinate.create2D(1159475.3267132244, 596787.4007584371),
                    Coordinate.create2D(1209500.4959743093, 604338.3697035066),
                    Coordinate.create2D(1226018.2405416488, 570830.9450097609),
                    Coordinate.create2D(1268964.3764167312, 621799.9853889795),
                    Coordinate.create2D(1220826.9493919136, 631710.6321293833),
                    Coordinate.create2D(1192510.8158479033, 634070.3099247174),
                    Coordinate.create2D(1192510.8158479033, 634070.3099247174),
                    Coordinate.create2D(1191095.0091707027, 657195.1523189925)),
                    Dimension.Two
                ),
                new ArrayList<LinearRing>(),
                Dimension.Two
            );
        String wkt = "POLYGON ((1191095.0091707027 657195.1523189925, 1159475.3267132244 596787.4007584371, " +
                "1209500.4959743093 604338.3697035066, 1226018.2405416488 570830.9450097609, " +
                "1268964.3764167312 621799.9853889795, 1220826.9493919136 631710.6321293833, " +
                "1192510.8158479033 634070.3099247174, 1192510.8158479033 634070.3099247174, " +
                "1191095.0091707027 657195.1523189925))";
        assertEquals(wkt, writer.write(polygon));
    }

    @Test
    public void circularString() {
        WKTWriter writer = new WKTWriter();
        CircularString cs = new CircularString(Arrays.asList(
                Coordinate.create2D(1, 1),
                Coordinate.create2D(5, 5),
                Coordinate.create2D(2, 2)
        ), Dimension.Two);
        assertEquals("CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)", writer.write(cs));
    }

}
