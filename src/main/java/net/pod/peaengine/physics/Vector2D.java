package net.pod.peaengine.physics;

import java.util.Vector;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates an exact copy of this vector
     * @return new vector object with same x and y values
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * Moves a vector along X axis
     * @param t move distance. Positive to move right, negative to move left
     */
    public void moveX(double t) {
        x += t;
    }

    /**
     * Moves a vector along Y axis
     * @param t move distance. Positive to move up, negative to move down
     */
    public void moveY(double t) {
        y += t;
    }

    /**
     * Calculates vector length
     * @return length of this vector
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Adds two vectors together
     * @param other vector to add with this one
     * @return new vector object that is a sum of this and other
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * If you need to read documentation for this one, i doubt you are qualified enough to do anything, please leave<br>
     * <br><br><br>
     * Still here?<br>
     * <br>
     * Ugh, fiiiiiiineeeee....<br>
     * <br>
     * Subtracts other vector from this vector
     * @param other vector to subtract from this
     * @return new vector object that is subtraction result
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Scales a vector by some scalar
     * @param scalar scalar to scale
     * @return new vector object that is this vector scaled by scalar
     */
    public Vector2D scale(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Measures the cosine of the angle between two vectors
     * @param other another vector in pair to form angle
     * @return
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Measures length of a line between two points .
     * In other words, measures distance between points
     * @param other second point
     * @return distance between this and other
     */
    public double distance(Vector2D other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    /**
     * Measures angle between this vector and some other vector
     * @param other other vector to form an angle
     * @return angle in radians between this and other vector
     */
    public double angleBetween(Vector2D other) {
        double dotProduct = this.dot(other);
        double magnitudes = this.magnitude() * other.magnitude();
        return Math.acos(dotProduct / magnitudes);
    }

    /**
     * Calculates a reflection of the vector
     * @param normal
     * @return idk actually, but programmatically - a new vector object with the result
     */
    public Vector2D reflect(Vector2D normal) {
        double dotProduct = this.dot(normal);
        return this.subtract(normal.scale(2 * dotProduct));
    }

    /**
     * Projects a vector onto other vector
     * @param other vector to project onto
     * @return new vector objects, that is projection of this to other
     */
    public Vector2D projectOnto(Vector2D other) {
        double dotProduct = this.dot(other);
        double otherMagnitudeSquared = other.magnitude() * other.magnitude();
        double scalar = dotProduct / otherMagnitudeSquared;
        return other.scale(scalar);
    }

    /**
     * Normalizes this vector without changing the object
     * @return new vector object that is this vector normalised
     */
    public Vector2D normalized() {
        double magnitude = Math.sqrt(x * x + y * y);
        if (magnitude == 0) {
            throw new ArithmeticException("Cannot normalize a zero vector.");
        }
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * Rotates the vector by some angle
     * @param angle rotation angle in radians
     * @return new vector object that is this vector rotated by angle
     */
    public Vector2D rotated(double angle) {
        double radians = Math.toRadians(angle);
        double cosTheta = Math.cos(radians);
        double sinTheta = Math.sin(radians);
        double newX = this.x * cosTheta - this.y * sinTheta;
        double newY = this.x * sinTheta + this.y * cosTheta;
        return new Vector2D(newX, newY);
    }

    /**
     * Mirrors a vector around 0;0
     * @return new object that is this vector inverted
     */
    public Vector2D inverted() {
        return new Vector2D(-x, -y);
    }
    //TODO: write documentation for them hard ones
    public Vector2D mirroredAround(Vector2D v) {
        double dotProduct = this.dot(v);
        double magnitudeSquared = v.magnitude();
        double projectionScalar = dotProduct / magnitudeSquared;
        Vector2D projection = new Vector2D(projectionScalar * v.x, projectionScalar * v.y);

        double mirroredX = 2 * projection.x - this.x;
        double mirroredY = 2 * projection.y - this.y;
        return new Vector2D(mirroredX, mirroredY);
    }

    public Vector2D mirroredAroundLine(Vector2D linePoint1, Vector2D linePoint2) {
        Vector2D lineVector = new Vector2D(linePoint2.x - linePoint1.x, linePoint2.y - linePoint1.y);
        Vector2D toOriginal = new Vector2D(this.x - linePoint1.x, this.y - linePoint1.y);

        double dotProduct = toOriginal.dot(lineVector);
        double magnitudeSquared = lineVector.magnitude();
        double projectionScalar = dotProduct / magnitudeSquared;

        Vector2D projection = new Vector2D(projectionScalar * lineVector.x, projectionScalar * lineVector.y);
        double mirroredX = 2 * projection.x - toOriginal.x;
        double mirroredY = 2 * projection.y - toOriginal.y;

        return new Vector2D(mirroredX + linePoint1.x, mirroredY + linePoint1.y);
    }

    public static Vector2D lerp(Vector2D start, Vector2D end, double t) {
        double x = start.x + t * (end.x - start.x);
        double y = start.y + t * (end.y - start.y);
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
