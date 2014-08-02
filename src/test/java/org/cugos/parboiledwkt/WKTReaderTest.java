package org.cugos.parboiledwkt;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The WKTReader Unit Test
 * @author Jared Erickson
 */
public class WKTReaderTest {

    private final WKTWriter writer = new WKTWriter();

    private final WKTReader reader = new WKTReader();

    @Test
    public void point() {
        String wkt = "POINT (1.0 2.0)";
        Point point = (Point) reader.read(wkt);
        assertEquals(wkt, writer.write(point));
    }

    @Test
    public void circularString() {
        String wkt = "CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)";
        CircularString cs = (CircularString) reader.read(wkt);
        assertEquals(wkt, writer.write(cs));
    }

}
