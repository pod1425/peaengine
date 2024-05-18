package net.pod.peaengine.gameloop;

import java.lang.reflect.InvocationTargetException;

public class GameLoop {
    private static GameLoopExecutor executor = null;
    // usage of reflection to allow anyone to create their implementation of game loop
    private static Class<? extends GameLoopExecutor> type = FixedDeltaLoop.class;

    /**
     * Changes the type of game loop implementation used ({@link FixedDeltaLoop} by default)
     * for later creation and running of render and update pipelines
     * @param type of the game loop that will be used
     * @throws IllegalStateException if the loop is already running
     */
    public static void setLoopType(Class<? extends GameLoopExecutor> type) {
        if (executor != null) {
            throw new IllegalStateException("Cant change type of running game loop");
        }
        GameLoop.type = type;
    }

    /**
     * Creates an instance of {@link GameLoopExecutor} and runs it
     * @param updatePipeline
     * @param renderPipeline
     * @throws RuntimeException if creating an instance of game loop was failed
     */
    public static void launch(Runnable updatePipeline, Runnable renderPipeline) {
        try {
            executor = type
                    .getDeclaredConstructor(Runnable.class, Runnable.class)
                    .newInstance(updatePipeline, renderPipeline);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        executor.start();
        // maybe probably? gotta research
        // executor.setDaemon(true);
    }
}