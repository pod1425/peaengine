package net.pod.peaengine.interract;


import net.pod.peaengine.physics.GameObject;
import net.pod.peaengine.physics.Vector2D;

public class AABBCollider implements Collider {
    private Vector2D min; // bottom-left
    private Vector2D max; // top-right
    private GameObject obj;

    public AABBCollider(Vector2D min, Vector2D max, GameObject obj) {
        this.min = min.copy();
        this.max = max.copy();
        this.obj = obj;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof AABBCollider) {
            AABBCollider aabb = (AABBCollider) other;
            return (this.min.x < aabb.max.x && this.max.x > aabb.min.x &&
                    this.min.y < aabb.max.y && this.max.y > aabb.min.y);
        }
        return false;
    }

    @Override
    public boolean isPointInside(Vector2D point) {
        return (point.x >= min.x && point.x <= max.x &&
                point.y >= min.y && point.y <= max.y);
    }

    @Override
    public void setPosition(Vector2D position) {
        Vector2D size = max.subtract(min);
        this.min = position;
        this.max = position.add(size);
    }

    @Override
    public Vector2D getMin() {
        return min;
    }

    @Override
    public Vector2D getMax() {
        return max;
    }

    public GameObject getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "AABBCollider{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

    @Override
    public boolean triggeredOn(Vector2D point) {
        return isPointInside(point);
    }
}
