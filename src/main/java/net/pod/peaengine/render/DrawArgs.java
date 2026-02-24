package net.pod.peaengine.render;

import net.pod.peaengine.physics.Vector2D;

public class DrawArgs {
    private Vector2D pos;
    private float hScale;
    private float vScale;

    private DrawArgs() {

    }

    public static DrawArgs onPos(Vector2D pos) {
        DrawArgs args = new DrawArgs();
        args.pos = pos;
        args.hScale = 1f;
        args.vScale = 1f;

        return args;
    }

    public DrawArgs ofScale(float scale) {
        hScale = scale;
        vScale = scale;
        return this;
    }

    public DrawArgs ofHvScale(float hScale, float vScale) {
        this.hScale = hScale;
        this.vScale = vScale;
        return this;
    }

    public Vector2D getPos() {
        return pos;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    public float gethScale() {
        return hScale;
    }

    public void sethScale(float hScale) {
        this.hScale = hScale;
    }

    public float getvScale() {
        return vScale;
    }

    public void setvScale(float vScale) {
        this.vScale = vScale;
    }
}
