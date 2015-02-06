package org.cugos.parboiledwkt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import org.cugos.parboiledwkt.WKB.Endian;
import org.cugos.parboiledwkt.WKB.GeometryType;
import org.cugos.parboiledwkt.WKB.GeometryTypeFlag;

/**
 * A WKB and EWKB Geometry Reader
 * @author Jared Erickson
 */
public class WKBReader {

    /**
     * Read a Geometry from an array of bytes.
     * @param bytes The array of bytes
     * @return A Geometry or null
     */
    public Geometry read(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return read(buffer);
    }

    /**
     * Read a Geometry from a hex String
     * @param hex The hex String
     * @return A Geometry or null
     */
    public Geometry read(String hex) {
        return read(toBytes(hex));
    }

    /**
     * Read a Geometry from a ByteBuffer
     * @param buffer The ByteBuffer
     * @return A Geometry or null
     */
    private Geometry read(ByteBuffer buffer) {

        // Determine byte order
        Endian endian = Endian.get(buffer.get());
        if (endian == Endian.Big) {
            buffer.order(ByteOrder.BIG_ENDIAN);
        } else {
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }

        // Determine geometry type
        GeometryType geometryType = GeometryType.get(buffer.getInt());

        // Determine dimension
        boolean hasM = false;
        boolean hasZ = false;
        boolean hasSRID = false;
        if ((geometryType.getValue() & GeometryTypeFlag.M.getValue()) == GeometryTypeFlag.M.getValue()) {
            hasM = true;
        }
        if ((geometryType.getValue() & GeometryTypeFlag.Z.getValue()) == GeometryTypeFlag.Z.getValue()) {
            hasZ = true;
        }
        if ((geometryType.getValue() & GeometryTypeFlag.SRID.getValue()) == GeometryTypeFlag.SRID.getValue()) {
            hasSRID = true;
        }

        Dimension dimension;
        if (hasZ && hasM) {
            dimension = Dimension.ThreeMeasured;
        } else if (hasZ) {
            dimension = Dimension.Three;
        } else if (hasM) {
            dimension = Dimension.TwoMeasured;
        } else {
            dimension = Dimension.Two;
        }

        // Extract SRID if present
        String srid = null;
        if (hasSRID) {
            srid = String.valueOf(buffer.getInt());
        }

        // Extract Geometry
        if (geometryType == GeometryType.Point) {
            Coordinate coordinate = getCoordinate(buffer, dimension);
            return new Point(coordinate, dimension, srid);
        } else if (geometryType == GeometryType.LineString) {
            int numberOfCoordinates = buffer.getInt();
            List<Coordinate> coordinates = new ArrayList<Coordinate>(numberOfCoordinates);
            for(int i = 0; i<numberOfCoordinates; i++) {
                coordinates.add(getCoordinate(buffer, dimension));
            }
            return new LineString(coordinates, dimension, srid);
        } else if (geometryType == GeometryType.Polygon) {
            int numberOfRings = buffer.getInt();
            List<LinearRing> rings = new ArrayList<LinearRing>(numberOfRings);
            for (int i = 0; i < numberOfRings; i++) {
                int numberOfCoordinates = buffer.getInt();
                List<Coordinate> coordinates = new ArrayList<Coordinate>(numberOfCoordinates);
                for (int j = 0; j < numberOfCoordinates; j++) {
                    coordinates.add(getCoordinate(buffer, dimension));
                }
                rings.add(new LinearRing(coordinates, dimension, srid));
            }
            return new Polygon(rings.get(0), rings.subList(1, rings.size()), dimension, srid);
        } else if (geometryType == GeometryType.MultiPoint) {
            int numberOfPoints = buffer.getInt();
            List<Point> points = new ArrayList<Point>(numberOfPoints);
            for (int i = 0; i < numberOfPoints; i++) {
                Point point = (Point) read(buffer);
                points.add(point);
            }
            return new MultiPoint(points, dimension, srid);
        } else if (geometryType == GeometryType.MultiLineString) {
            int numberOfLines = buffer.getInt();
            List<LineString> lines = new ArrayList<LineString>(numberOfLines);
            for (int i = 0; i < numberOfLines; i++) {
                LineString line = (LineString) read(buffer);
                lines.add(line);
            }
            return new MultiLineString(lines, dimension, srid);
        } else if (geometryType == GeometryType.MultiPolygon) {
            int numberOfPolygons = buffer.getInt();
            List<Polygon> polygons = new ArrayList<Polygon>(numberOfPolygons);
            for (int i = 0; i < numberOfPolygons; i++) {
                Polygon polygon = (Polygon) read(buffer);
                polygons.add(polygon);
            }
            return new MultiPolygon(polygons, dimension, srid);
        } else if (geometryType == GeometryType.GeometryCollection) {
            int numberOfGeometries = buffer.getInt();
            List<Geometry> geometries = new ArrayList<Geometry>(numberOfGeometries);
            for (int i = 0; i < numberOfGeometries; i++) {
                Geometry geometry = read(buffer);
                geometries.add(geometry);
            }
            return new GeometryCollection(geometries, dimension, srid);
        } else if (geometryType == GeometryType.CircularString) {
            int numberOfCoordinates = buffer.getInt();
            List<Coordinate> coordinates = new ArrayList<Coordinate>(numberOfCoordinates);
            for(int i = 0; i<numberOfCoordinates; i++) {
                coordinates.add(getCoordinate(buffer, dimension));
            }
            return new CircularString(coordinates, dimension, srid);
        } else if (geometryType == GeometryType.Triangle) {
            int numberOfRings = buffer.getInt();
            List<LinearRing> rings = new ArrayList<LinearRing>(numberOfRings);
            for (int i = 0; i < numberOfRings; i++) {
                int numberOfCoordinates = buffer.getInt();
                List<Coordinate> coordinates = new ArrayList<Coordinate>(numberOfCoordinates);
                for (int j = 0; j < numberOfCoordinates; j++) {
                    coordinates.add(getCoordinate(buffer, dimension));
                }
                rings.add(new LinearRing(coordinates, dimension, srid));
            }
            return new Triangle(rings.get(0), rings.subList(1, rings.size()), dimension, srid);
        } else if (geometryType == GeometryType.Tin) {
            int numberOfTriangles = buffer.getInt();
            List<Triangle> triangles = new ArrayList<Triangle>(numberOfTriangles);
            for (int i = 0; i < numberOfTriangles; i++) {
                Triangle triangle = (Triangle) read(buffer);
                triangles.add(triangle);
            }
            return new Tin(triangles, dimension, srid);
        } else if (geometryType == GeometryType.CompoundCurve) {
            int numberOfCurves = buffer.getInt();
            List<Curve> curves = new ArrayList<Curve>(numberOfCurves);
            for (int i = 0; i < numberOfCurves; i++) {
                Curve curve = (Curve) read(buffer);
                curves.add(curve);
            }
            return new CompoundCurve(curves, dimension, srid);
        } else if (geometryType == GeometryType.MultiCurve) {
            int numberOfCurves = buffer.getInt();
            List<Curve> curves = new ArrayList<Curve>(numberOfCurves);
            for (int i = 0; i < numberOfCurves; i++) {
                Curve curve = (Curve) read(buffer);
                curves.add(curve);
            }
            return new MultiCurve(curves, dimension, srid);
        } else if (geometryType == GeometryType.CurvePolygon) {
            int numberOfCurves = buffer.getInt();
            List<Curve> curves = new ArrayList<Curve>(numberOfCurves);
            for (int i = 0; i < numberOfCurves; i++) {
                Curve curve = (Curve) read(buffer);
                curves.add(curve);
            }
            return new CurvePolygon(curves.get(0), curves.subList(1, curves.size()), dimension, srid);
        } else if (geometryType == GeometryType.MultiSurface) {
            int numberOfSurfaces = buffer.getInt();
            List<Surface> surfaces = new ArrayList<Surface>(numberOfSurfaces);
            for (int i = 0; i < numberOfSurfaces; i++) {
                Surface surface = (Surface) read(buffer);
                surfaces.add(surface);
            }
            return new MultiSurface(surfaces, dimension, srid);
        } else if (geometryType == GeometryType.PolyHedralSurface) {
            int numberOfPolygons = buffer.getInt();
            List<Polygon> polygons = new ArrayList<Polygon>(numberOfPolygons);
            for (int i = 0; i < numberOfPolygons; i++) {
                Polygon polygon = (Polygon) read(buffer);
                polygons.add(polygon);
            }
            return new PolyHedralSurface(polygons, dimension, srid);
        }
        return null;
    }

    /**
     * Get Coordinate of the given Dimension from the ByteBuffer
     * @param buffer The ByteBuffer
     * @param dimension The Dimension
     * @return A Coordinate
     */
    private Coordinate getCoordinate(ByteBuffer buffer, Dimension dimension) {
        double x = buffer.getDouble();
        double y = buffer.getDouble();
        double z = Double.NaN;
        double m = Double.NaN;
        if (dimension == Dimension.Three || dimension == Dimension.ThreeMeasured) {
            z = buffer.getDouble();
        }
        if (dimension == Dimension.ThreeMeasured || dimension == Dimension.TwoMeasured) {
            m = buffer.getDouble();
        }
        return new Coordinate(x,y,z,m);
    }

    /**
     * Convert a hex String into a byte Array
     * @param hexString The hex String
     * @return An array of bytes
     */
    private static byte[] toBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}