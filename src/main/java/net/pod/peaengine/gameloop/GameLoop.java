package net.pod.peaengine.gameloop;

import net.pod.peaengine.gameloop.old.FixedDeltaLoop;
import net.pod.peaengine.window.WindowProps;
import org.lwjgl.system.windows.WinBase;

import java.lang.reflect.InvocationTargetException;

public class GameLoop {
    private static GameLoopExecutor executor = null;
    // usage of reflection to allow anyone to create their implementation of game loop
    private static Class<? extends GameLoopExecutor> type = DeltaTimeLoop.class;

    /**
     * Changes the type of game loop implementation used ({@link DeltaTimeLoop} by default)
     * for later creation and running of render and update pipelines
     * @param type of the game loop that will be used
     * @throws IllegalStateException if the loop is already running
     */
    public static void setLoopType(Class<? extends GameLoopExecutor> type) {
        if (executor != null) {
            throw new IllegalStateException("Can't change type of running game loop");
        }
        GameLoop.type = type;
    }

    /**
     * Gets current delta time
     * @throws IllegalStateException if the loop is not running
     * @return delta time
     */
    public static long getDeltaTime() {
        if (executor == null) {
            throw new IllegalStateException("Executor cannot be null");
        }
        return executor.getDeltaTime();
    }

    /**
     * Creates an instance of {@link GameLoopExecutor} and runs it
     * @param updatePipeline
     * @param renderPipeline
     * @throws RuntimeException if creating an instance of game loop was failed
     */
    public static void launch(WindowProps props, Runnable updatePipeline, Runnable renderPipeline) {
        try {
            executor = type
                    .getDeclaredConstructor(Runnable.class, Runnable.class)
                    .newInstance(updatePipeline, renderPipeline);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        executor.startLoop(props);
        // maybe probably? gotta research
        //executor.setDaemon(true);
    }

    public static int getFps() {
        return executor.getCurrentFps();
    }

    public static long getCurrentTick() {
        return executor.getCurrentTick();
    }
}