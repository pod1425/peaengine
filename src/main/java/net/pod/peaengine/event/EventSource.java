package net.pod.peaengine.event;

/**
 * This interface dictates to potential event sources how they should add and remove listeners
 * @param <T> Any type of {@link EventArgs}
 */
public interface EventSource<T extends EventArgs> {
    /**
     * Adds a listener to underlying {@link EventListenerNotifier}
     * @param listener listener to be added
     */
    void addEventListener(EventListener<T> listener);

    /**
     * Removes a listener from underlying {@link EventListenerNotifier}
     * @param listener listener to be removed
     */
    void removeEventListener(EventListener<T> listener);
}
