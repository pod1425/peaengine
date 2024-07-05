package net.pod.peaengine.physics;

public class Physics {
    private static Physics instance;
    private Vector2D gravity;
    private double airResistance;

    private Physics() {
        gravity = new Vector2D(0, -9.81);
        airResistance = 0.01;
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
}
