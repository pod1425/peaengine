package net.pod.peaengine.physics;

import net.pod.peaengine.event.EventListener;
import net.pod.peaengine.event.EventSource;
import net.pod.peaengine.event.InterractionEventArgs;
import net.pod.peaengine.interract.Collider;
import net.pod.peaengine.render.Drawable;
import net.pod.peaengine.render.texture.Texture;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameObject {
    // fuck encapsulation (for now)
    // TODO: encapsulation
    public int id;
    public String name;
    public Hitbox hitbox;
    public Rigidbody rigidbody;

    public Drawable sprite;

    public GameObject(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void dispose() {

    }
}
