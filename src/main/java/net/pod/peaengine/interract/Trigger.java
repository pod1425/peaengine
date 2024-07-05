package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

public interface Trigger {
    boolean triggeredOn(Vector2D point);
    default boolean justEntered(Vector2D oldPos, Vector2D currentPos) {
        return triggeredOn(currentPos) && !triggeredOn(oldPos);
    }

    default boolean justLeft(Vector2D oldPos, Vector2D currentPos) {
        return !triggeredOn(currentPos) && triggeredOn(oldPos);
    }
}
