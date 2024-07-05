package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

public class ProximityTrigger extends TriggerCircle {
    // counted from the center
    private double maxDetectDistance;
    public ProximityTrigger(Vector2D center, double triggerRadius, double maxDetectDistance) {
        super(center, triggerRadius);
    }

    public double getMaxDetectDistance() {
        return maxDetectDistance;
    }

    public void setMaxDetectDistance(double maxDetectDistance) {
        this.maxDetectDistance = maxDetectDistance;
    }

    public double changeMaxDetectDistance(double coefficient) {
        return maxDetectDistance += coefficient;
    }

    public double getAbsoluteDistance(Vector2D other) {
        return center.distance(other);
    }

    /**
     * Calculates how close point is to trigger center and returns a value
     * in range 0 to 1 telling how close point is to trigger center.
     *
     * @param other point to evaluate proximity
     * @return proximity value from 0 to 1, where 0 is when point is outside
     * detection distance, and 1 is when point is exactly at the center of trigger
     */
    public double getProximity(Vector2D other) {
        double distance = getAbsoluteDistance(other);
        if (distance > maxDetectDistance) {
            return 0.0;
        } else if (distance == 0) {
            return 1.0;
        }
        return (maxDetectDistance - distance) / maxDetectDistance;
    }
}
