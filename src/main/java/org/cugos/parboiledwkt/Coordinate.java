package org.cugos.parboiledwkt;

/**
 * A Coordinate that can contain X, Y, Z, and M values
 * @author Jared Erickson
 */
public final class Coordinate {

    /**
     * The X value
     */
    private final double x;

    /**
     * The Y value
     */
    private final double y;

    /**
     * The Z value
     */
    private final double z;

    /**
     * The M value
     */
    private final double m;

    /**
     * Creae a new Coordinate
     * @param x The x value
     * @param y The y value
     * @param z The z value
     * @param m The m value
     */
    public Coordinate(double x, double y, double z, double m) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }

    /**
     * Create a Coordinate with only XY values
     * @param x The x value
     * @param y The y value
     */
    public Coordinate(double x, double y) {
        this(x, y, Double.NaN, Double.NaN);
    }

    /**
     * Create a 2D Coordinate
     * @param x The x value
     * @param y The y value
     * @return A 2D Coordinate
     */
    public static Coordinate create2D(double x, double y) {
        return new Coordinate(x, y, Double.NaN, Double.NaN);
    }

    /**
     * Create a 3D Coordinate
     * @param x The x value
     * @param y The y value
     * @param z The z value
     * @return A 3D Coordinate
     */
    public static Coordinate create3D(double x, double y, double z) {
        return new Coordinate(x, y, z, Double.NaN);
    }

    /**
     * Create a 2DM Coordinate
     * @param x The x value
     * @param y The y value
     * @param m The m value
     * @return A 2DM Coordinate
     */
    public static Coordinate create2DM(double x, double y, double m) {
        return new Coordinate(x, y, Double.NaN, m);
    }

    /**
     * Create a 3DM Coordinate
     * @param x The x value
     * @param y The y value
     * @param z The z value
     * @param m The m value
     * @return A 3DM Coordinate
     */
    public static Coordinate create3DM(double x, double y, double z, double m) {
        return new Coordinate(x, y, z, m);
    }

    /**
     * Get the x value
     * @return The x value
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y value
     * @return The y value
     */
    public double getY() {
        return y;
    }

    /**
     * Get the z value
     * @return The z value
     */
    public double getZ() {
        return z;
    }

    /**
     * Get the m vaue
     * @return The m value
     */
    public double getM() {
        return m;
    }

    /**
     * Get the Dimension
     * @return The Dimension
     */
    public Dimension getDimension() {
        if (!Double.isNaN(m) && !Double.isNaN(z)) {
            return Dimension.ThreeMeasured;
        } else if (!Double.isNaN(z)) {
            return Dimension.Three;
        } else if (!Double.isNaN(m)) {
            return Dimension.TwoMeasured;
        } else {
            return Dimension.Two;
        }
    }

    /**
     * Determine if the Coordinate is empty (x and y values of NaN)
     * @return Whether this Coordinate is empty
     */
    public boolean isEmpty() {
        return (Double.isNaN(x) && Double.isNaN(y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.m, m) != 0) return false;
        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        String str = "Coordinate { x = " + x + " y = " + y;
        if (!Double.isNaN(z)) {
            str = str + " z = " + z;
        }
        if (!Double.isNaN(m)) {
            str = str + " m = " + m;
        }
        str = str + " }";
        return str;
    }

    /**
     * A Builder for constructing immutable Coordinates
     */
    public static class Builder {

        private double x = Double.NaN;

        private double y = Double.NaN;

        private double z = Double.NaN;

        private double m = Double.NaN;

        public Builder setX(double x) {
            this.x = x;
            return this;
        }

        public Builder setY(double y) {
            this.y = y;
            return this;
        }

        public Builder setM(double m) {
            this.m = m;
            return this;
        }

        public Builder setZ(double z) {
            this.z = z;
            return this;
        }

        public Coordinate build() {
            Coordinate coordinate;
            // Three Measured
            if (!Double.isNaN(x) && !Double.isNaN(y) && !Double.isNaN(z) && !Double.isNaN(m)) {
                coordinate = new Coordinate(x, y, z, m);
            }
            // Three
            else if (!Double.isNaN(x) && !Double.isNaN(y) && !Double.isNaN(z) && Double.isNaN(m)) {
                coordinate = new Coordinate(x, y, z, Double.NaN);
            }
            // Two Measured
            else if (!Double.isNaN(x) && !Double.isNaN(y) && Double.isNaN(z) && !Double.isNaN(m)) {
                coordinate = new Coordinate(x, y, Double.NaN, m);
            }
            // Two
            else if (!Double.isNaN(x) && !Double.isNaN(y) && Double.isNaN(z) && Double.isNaN(m)) {
                coordinate = new Coordinate(x, y, Double.NaN, Double.NaN);
            }
            // Empty
            else {
                coordinate = new Coordinate(Double.NaN, Double.NaN, Double.NaN, Double.NaN);
            }
            reset();
            return coordinate;
        }

        private void reset() {
            this.x = Double.NaN;
            this.y = Double.NaN;
            this.z = Double.NaN;
            this.m = Double.NaN;
        }

    }

}
