package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

// TODO MOOOORE DOCUMENTATOIN
public class TriggerBox implements Trigger {
    private Vector2D LUCorner; // left upper corner
    private Vector2D RLCorner; // right lower corner


    public TriggerBox(Vector2D leftUpperCorner, Vector2D rightLowerCorner) {
        this.LUCorner = leftUpperCorner;
        this.RLCorner = rightLowerCorner;
    }

    public boolean triggeredOn(Vector2D point) {
        return (point.x >= LUCorner.x && point.x <= RLCorner.x && point.y <= LUCorner.y && point.y >= RLCorner.y);
    }

    public void move(Vector2D delta) {
        LUCorner = LUCorner.add(delta);
        RLCorner = RLCorner.add(delta);
    }

    public double getWidth() {
        return RLCorner.x - LUCorner.x;
    }

    public double getHeight() {
        return LUCorner.y - RLCorner.y;
    }

    @Override
    public String toString() {
        return "TriggerBox{" +
                "LUCorner=" + LUCorner +
                ", RLCorner=" + RLCorner +
                '}';
    }
}
