package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

public interface Collider extends Trigger {
    boolean isColliding(Collider other);
    boolean isPointInside(Vector2D point);
    void setPosition(Vector2D position);
    Vector2D getMin();
    Vector2D getMax();
}
