package net.pod.peaengine.physics;

import net.pod.peaengine.interract.Collideable;
import net.pod.peaengine.util.Quadtree;
import net.pod.peaengine.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static net.pod.peaengine.Engine.objects;

public class Physics {
    private static Physics instance;
    private Vector2D gravity;
    private double airResistance;

    private Quadtree quadtree;

    private Physics() {
        gravity = new Vector2D(0, -9.81);
        airResistance = 0.01;
        quadtree = new Quadtree(1, new Rectangle(-100, 100, 200, 200));
    }

    public static Physics getInstance() {
        if (instance == null) {
            instance = new Physics();
        }
        return instance;
    }

    public Vector2D getGravity() {
        return gravity;
    }

    public void setGravity(Vector2D gravity) {
        this.gravity = gravity;
    }

    public double getAirResistance() {
        return airResistance;
    }

    public void setAirResistance(double airResistance) {
        this.airResistance = airResistance;
    }

    public void applyGlobalForces(Rigidbody rigidbody, double deltaTime) {
        rigidbody.applyForce(gravity.scale(rigidbody.getMass()));
        Vector2D airResistanceForce = rigidbody.getVelocity().scale(-airResistance);
        rigidbody.applyForce(airResistanceForce);
    }

    public void update(double deltaTime) {
        quadtree.clear();
        for (GameObject obj : objects.values()) {
            if (obj.rigidbody != null) {
                quadtree.insert(obj.rigidbody);
            }
        }

        for (GameObject obj : objects.values()) {
            List<Collideable> returnObjects = new ArrayList<>();
            quadtree.retrieve(returnObjects, obj.rigidbody);
            for (Collideable other : returnObjects) {
                if (obj.rigidbody != other && obj.rigidbody.isColliding(other)) {
                    obj.rigidbody.handleCollision(other);
                }
            }
        }
    }
}
