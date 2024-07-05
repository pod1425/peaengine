package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

public interface Trigger {
    boolean triggered(Vector2D point);
    default boolean justEntered(Vector2D oldPos, Vector2D currentPos) {
        return triggered(currentPos) && !triggered(oldPos);
    }

    default boolean justLeft(Vector2D oldPos, Vector2D currentPos) {
        return !triggered(currentPos) && triggered(oldPos);
    }
}
