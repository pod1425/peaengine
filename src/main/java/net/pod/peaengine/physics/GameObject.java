package net.pod.peaengine.physics;

import net.pod.peaengine.event.EventListener;
import net.pod.peaengine.event.EventSource;
import net.pod.peaengine.event.InterractionEventArgs;
import net.pod.peaengine.render.DrawableComponent;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameObject implements EventSource<InterractionEventArgs> {
    private List<UpdatableComponent> updatableComponents = new ArrayList<>();
    private Deque<DrawableComponent> drawableComponents = new LinkedList<>();


    @Override
    public void addEventListener(EventListener<InterractionEventArgs> listener) {

    }

    @Override
    public void removeEventListener(EventListener<InterractionEventArgs> listener) {

    }
}
