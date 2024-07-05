package net.pod.peaengine.physics;

public class Rigidbody implements UpdatableComponent {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double mass;

    public Rigidbody(Vector2D position, double mass) {
        this.position = position;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.mass = mass;
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
}
