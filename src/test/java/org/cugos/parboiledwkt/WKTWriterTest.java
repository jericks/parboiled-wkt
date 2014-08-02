package org.cugos.parboiledwkt;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * The WKTWriter Unit Test
 * @author Jared Erickson
 */
public class WKTWriterTest {

    @Test
    public void point() {
        WKTWriter writer = new WKTWriter();
        assertEquals("POINT (1.0 2.0)", writer.write(new Point(Coordinate.create2D(1, 2), Dimension.Two)));
    }

    @Test
    public void circularString() {
        WKTWriter writer = new WKTWriter();
        assertEquals("CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)",
                writer.write(new CircularString(Arrays.asList(
                        Coordinate.create2D(1, 1),
                        Coordinate.create2D(5, 5),
                        Coordinate.create2D(2, 2)),
                        Dimension.Two)
                )
        );

    }

}
