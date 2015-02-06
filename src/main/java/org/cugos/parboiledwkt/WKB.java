package org.cugos.parboiledwkt;

/**
 * A holder for enums used in the WKBWriter and WKBReader
 * @author Jared Erickson
 */
public interface WKB {

    /**
     * The WKB standard
     */
    public static enum Type {
        WKB, EWKB;
    }

    /**
     * The Byte Order
     */
    public static enum Endian {
        Big(0),
        Little(1);

        private final int value;

        Endian(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static Endian get(int value) {
            for(Endian endian : values()) {
                if (endian.getValue() == value) {
                    return endian;
                }
            }
            return null;
        }
    }

    /**
     * The Geometry Type
     */
    public static enum GeometryType {
        Point(1),
        LineString(2),
        Polygon(3),
        MultiPoint(4),
        MultiLineString(5),
        MultiPolygon(6),
        GeometryCollection(7),
        CircularString(8),
        CompoundCurve(9),
        CurvePolygon(10),
        MultiCurve(11),
        MultiSurface(12),
        Curve(13),
        Surface(14),
        PolyHedralSurface(15),
        Tin(16),
        Triangle(17);

        private final int value;

        GeometryType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static GeometryType get(int value) {
            for(GeometryType gt : values()) {
                if (gt.getValue() == value) {
                    return gt;
                }
            }
            return null;
        }

    }

    /**
     * The Geometry Type Flags
     */
    public static enum GeometryTypeFlag {
        Z(0x80000000),
        M(0x40000000),
        SRID(0x20000000);

        private final int value;

        GeometryTypeFlag(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

}
