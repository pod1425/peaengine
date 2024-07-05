package net.pod.peaengine.physics;

import net.pod.peaengine.event.EventListener;
import net.pod.peaengine.event.InterractionEventArgs;
import net.pod.peaengine.interract.Trigger;

import java.util.List;

public class Hitbox implements EventListener<InterractionEventArgs> {
    private List<Trigger> triggers;
    private EventListener<InterractionEventArgs> hitHandler;

    public Hitbox(List<Trigger> triggers, EventListener<InterractionEventArgs> hitHandler) {
        this.triggers = triggers;
        this.hitHandler = hitHandler;
    }


    @Override
    public void handleEvent(InterractionEventArgs args) {
        Vector2D pos = args.getPosition();
        for (int i = 0; i < triggers.size(); i++) {
            if (triggers.get(i).triggeredOn(pos)) {
                hitHandler.handleEvent(args);
                break;
            }
        }
    }
}
