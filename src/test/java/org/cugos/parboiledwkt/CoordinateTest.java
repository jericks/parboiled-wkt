package org.cugos.parboiledwkt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The Coordinate Unit Test
 */
public class CoordinateTest {

    @Test
    public void create2dWithConstructor() {
        Coordinate c = new Coordinate(1, 2);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertTrue(Double.isNaN(c.getZ()));
        assertTrue(Double.isNaN(c.getM()));
        assertEquals(Dimension.Two, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 }", c.toString());
    }

    @Test
    public void create2dWithStatic() {
        Coordinate c = Coordinate.create2D(1, 2);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertTrue(Double.isNaN(c.getZ()));
        assertTrue(Double.isNaN(c.getM()));
        assertEquals(Dimension.Two, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 }", c.toString());
    }

    @Test
    public void create2dmWithConstructor() {
        Coordinate c = new Coordinate(1, 2, Double.NaN, 4);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertTrue(Double.isNaN(c.getZ()));
        assertEquals(4, c.getM(), 0.1);
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 m = 4.0 }", c.toString());
    }

    @Test
    public void create2dmWithStatic() {
        Coordinate c = Coordinate.create2DM(1, 2, 4);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertTrue(Double.isNaN(c.getZ()));
        assertEquals(4, c.getM(), 0.1);
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 m = 4.0 }", c.toString());
    }

    @Test
    public void create3dWithConstructor() {
        Coordinate c = new Coordinate(1, 2, 3, Double.NaN);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertEquals(3, c.getZ(), 0.1);
        assertTrue(Double.isNaN(c.getM()));
        assertEquals(Dimension.Three, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 z = 3.0 }", c.toString());
    }

    @Test
    public void create3dWithStatic() {
        Coordinate c = Coordinate.create3D(1, 2, 3);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertEquals(3, c.getZ(), 0.1);
        assertTrue(Double.isNaN(c.getM()));
        assertEquals(Dimension.Three, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 z = 3.0 }", c.toString());
    }

    @Test
    public void create3dmWithConstructor() {
        Coordinate c = new Coordinate(1, 2, 3, 4);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertEquals(3, c.getZ(), 0.1);
        assertEquals(4, c.getM(), 0.1);
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 z = 3.0 m = 4.0 }", c.toString());
    }

    @Test
    public void create3dmStatic() {
        Coordinate c = Coordinate.create3DM(1, 2, 3, 4);
        assertEquals(1, c.getX(), 0.1);
        assertEquals(2, c.getY(), 0.1);
        assertEquals(3, c.getZ(), 0.1);
        assertEquals(4, c.getM(), 0.1);
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertFalse(c.isEmpty());
        assertEquals("Coordinate { x = 1.0 y = 2.0 z = 3.0 m = 4.0 }", c.toString());
    }

    @Test
    public void equalsAndHashCode() {
        // Equals
        assertTrue(Coordinate.create2D(1, 2).equals(Coordinate.create2D(1, 2)));
        assertTrue(Coordinate.create2DM(1, 2, 3).equals(Coordinate.create2DM(1, 2, 3)));
        assertTrue(Coordinate.create3D(1, 2, 3).equals(Coordinate.create3D(1, 2, 3)));
        assertTrue(Coordinate.create3DM(1, 2, 3, 4).equals(Coordinate.create3DM(1, 2, 3, 4)));
        assertFalse(Coordinate.create2D(1, 2).equals(Coordinate.create2D(4, 5)));
        assertFalse(Coordinate.create2DM(1, 2, 3).equals(Coordinate.create3D(1, 2, 3)));
        assertFalse(Coordinate.create2DM(1, 2, 3).equals(Coordinate.create3DM(1, 2, 3, 4)));

        // HashCode
        assertTrue(Coordinate.create2D(1, 2).hashCode() == Coordinate.create2D(1, 2).hashCode());
        assertTrue(Coordinate.create2DM(1, 2, 3).hashCode() == Coordinate.create2DM(1, 2, 3).hashCode());
        assertTrue(Coordinate.create3D(1, 2, 3).hashCode() == Coordinate.create3D(1, 2, 3).hashCode());
        assertTrue(Coordinate.create3DM(1, 2, 3, 4).hashCode() == Coordinate.create3DM(1, 2, 3, 4).hashCode());
        assertFalse(Coordinate.create2D(1, 2).hashCode() == Coordinate.create2D(4, 5).hashCode());
        assertFalse(Coordinate.create2DM(1, 2, 3).hashCode() == Coordinate.create3D(1, 2, 3).hashCode());
        assertFalse(Coordinate.create2DM(1, 2, 3).hashCode() == Coordinate.create3DM(1, 2, 3, 4).hashCode());
    }

    @Test
    public void builder() {
        Coordinate.Builder builder = new Coordinate.Builder();
        Coordinate c = builder.setX(1).setY(2).build();
        assertEquals(Coordinate.create2D(1, 2), c);
        c = builder.setX(1).setY(2).setM(3).build();
        assertEquals(Coordinate.create2DM(1, 2, 3), c);
        c = builder.setX(1).setY(2).setZ(3).build();
        assertEquals(Coordinate.create3D(1, 2, 3), c);
        c = builder.setX(1).setY(2).setZ(3).setM(4).build();
        assertEquals(Coordinate.create3DM(1, 2, 3, 4), c);
    }


}
