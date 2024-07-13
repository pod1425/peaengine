package net.pod.peaengine.physics;

import net.pod.peaengine.interract.Collideable;
import net.pod.peaengine.interract.Collider;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Rigidbody implements Collideable {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double mass;
    private boolean gravityAffected;
    // if the object is fixed, it cannot be moved by anything
    private boolean fixed;
    private Collider collider;

    private BiConsumer<Collideable, Collideable> collisionHandler;

    public Rigidbody(Vector2D position, double mass) {
        this.position = position;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.mass = mass;
        fixed = false;
        gravityAffected = true;
    }



    public void applyForce(Vector2D force) {
        Vector2D forceAcc = force.scale(1 / mass);
        acceleration = acceleration.add(forceAcc);
    }

    public void update(double deltaTime) {
        velocity = velocity.add(acceleration.scale(deltaTime));
        position = position.add(velocity.scale(deltaTime));
        // Reset acceleration after each update
        acceleration = new Vector2D(0, 0);
    }

    public Vector2D getPosition() {
        return position;
    }



    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    @Override
    public boolean isColliding(Collideable other) {
        return collider.isColliding(other.getCollider());
    }

    @Override
    public boolean isPointInside(Vector2D point) {
        return collider.isPointInside(point);
    }
    @Override
    public Collider getCollider() {
        return collider;
    }

    @Override
    public void handleCollision(Collideable other) {
        collisionHandler.accept(this, other);
    }

    public void setCollisionHandler(BiConsumer<Collideable, Collideable> collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public boolean isGravityAffected() {
        return gravityAffected;
    }

    public void setGravityAffected(boolean gravityAffected) {
        this.gravityAffected = gravityAffected;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
