package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

public class TriggerCircle implements Trigger {
    protected Vector2D center;
    protected double triggerRadius;

    public TriggerCircle(Vector2D center, double triggerRadius) {
        this.center = center;
        this.triggerRadius = triggerRadius;
    }

    public Vector2D getCenter() {
        return center;
    }

    public void setCenter(Vector2D center) {
        this.center = center;
    }

    public double getTriggerRadius() {
        return triggerRadius;
    }

    public void setTriggerRadius(double triggerRadius) {
        this.triggerRadius = triggerRadius;
    }

    @Override
    public boolean triggered(Vector2D point) {
        return center.distance(point) < triggerRadius;
    }


}
