package net.pod.peaengine.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Wrapper for List of event listeners
 * @param <T> Any type of {@link EventArgs}
 */
public class EventListenerNotifier<T extends EventArgs> {
    private List<EventListener<T>> listeners = new ArrayList<>();
    // its static and WILL be shared between all EventListenerNotifier instances on purpose
    // because having multiple ExecutorServices at the same time is a NIGHTMARE
    private static ExecutorService executorService;
    public static final int FORCE_SHUTDOWN_TIME = 1000;

    public static void init() {
        executorService = Executors.newCachedThreadPool();
    }

    public void addEventListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    public void removeEventListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies
     * @param eventArgs
     */
    public void notifyListeners(T eventArgs) {
        for (EventListener<T> listener : listeners) {
            executorService.submit(() -> listener.handleEvent(eventArgs));
        }
    }
    public static void dispose() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(FORCE_SHUTDOWN_TIME, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
