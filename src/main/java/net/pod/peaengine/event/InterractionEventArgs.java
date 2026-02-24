package net.pod.peaengine.event;

import net.pod.peaengine.physics.GameObject;
import net.pod.peaengine.physics.Vector2D;

public class InterractionEventArgs extends EventArgs {
    protected Vector2D position = null;
    protected GameObject target;

    public InterractionEventArgs(GameObject sender, GameObject target, Object argument) {
        super(sender, argument);
        this.target = target;
    }

    public Vector2D getPosition() {
        return position;
    }

    public GameObject getTarget() {
        return target;
    }
}
