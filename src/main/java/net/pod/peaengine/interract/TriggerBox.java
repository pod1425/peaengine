package net.pod.peaengine.interract;

import net.pod.peaengine.physics.Vector2D;

// TODO MOOOORE DOCUMENTATOIN
public class TriggerBox implements Trigger {
    private Vector2D LUCorner; // left upper
    private Vector2D RUCorner; // right upper
    private Vector2D RLCorner; // right lower
    private Vector2D LLCorner; // left lower

    /**
     * Constructs a trigger box in this order:
     *  1-----2
     *  |     |
     *  4-----3
     *
     * Where numbers are the order of parameters
     */
    public TriggerBox(Vector2D LUCorner, Vector2D RUCorner, Vector2D RLCorner, Vector2D LLCorner) {
        this.LUCorner = LUCorner;
        this.RUCorner = RUCorner;
        this.RLCorner = RLCorner;
        this.LLCorner = LLCorner;
    }

    public boolean triggered(Vector2D point) {
        return (point.x >= LUCorner.x && point.x <= RUCorner.x && point.y <= LUCorner.y && point.y >= LLCorner.y);
    }

    public void move(Vector2D delta) {
        LUCorner = LUCorner.add(delta);
        RUCorner = RUCorner.add(delta);
        RLCorner = RLCorner.add(delta);
        LLCorner = LLCorner.add(delta);
    }

    public double getWidth() {
        return RUCorner.x - LUCorner.x;
    }

    public double getHeight() {
        return LUCorner.y - LLCorner.y;
    }

    @Override
    public String toString() {
        return "TriggerBox{" +
                "LUCorner=" + LUCorner +
                ", RUCorner=" + RUCorner +
                ", RLCorner=" + RLCorner +
                ", LLCorner=" + LLCorner +
                '}';
    }
}
