package net.pod.peaengine.gameloop;

import net.pod.peaengine.Engine;
import org.lwjgl.glfw.GLFW;

public class DeltaTimeLoop extends GameLoopExecutor {
    private long lastTime;
    private long currentTime;

    public DeltaTimeLoop(Runnable runBefore, Runnable updatePipeline, Runnable renderPipeline) {
        super(runBefore, updatePipeline, renderPipeline);
        lastTime = System.nanoTime();
    }

    @Override
    public void run() {
        initializeRenderingContext();
        Engine.loadResources();
        runBefore.run();

        GLFW.glfwSwapInterval(1); // v-sync

        // remove the object
        launchProps = null;

        while (Engine.shouldRun) {
            currentTime = System.nanoTime();
            deltaTime = currentTime - lastTime;
            currentTick++;
            updatePipeline.run();
            beforeRender();
            renderPipeline.run();
            afterRender();

            lastTime = currentTime;
        }
    }

    @Override
    public int getCurrentFps() {
        return deltaTime == 0 ? 0 : (int)(1_000_000_000 / deltaTime);
    }
}