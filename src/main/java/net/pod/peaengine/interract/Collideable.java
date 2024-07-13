package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

public interface Collideable {
    boolean isColliding(Collideable other);
    boolean isPointInside(Vector2D point);
    void setPosition(Vector2D position);
    Collider getCollider();
    void handleCollision(Collideable other);
}
