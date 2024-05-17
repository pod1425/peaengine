package net.pod.peaengine.event;

/**
 * Listener for the event. This interface defines that some objects can respond to events being fired
 * @param <T> Any type of {@link EventArgs}
 */
public interface EventListener<T extends EventArgs> {
    /**
     * This method is called when an event is fired in another class, where this event is added as listener.
     * @param args arguments of an event that contain the event source and some other info
     */
    void handleEvent(T args);
}
