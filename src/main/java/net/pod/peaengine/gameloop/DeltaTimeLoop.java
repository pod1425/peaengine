package net.pod.peaengine.gameloop;

import net.pod.peaengine.Engine;
import net.pod.peaengine.window.Window;
import org.lwjgl.glfw.GLFW;

public class DeltaTimeLoop extends GameLoopExecutor {
    private long lastTime;
    private long currentTime;

    public DeltaTimeLoop(Runnable updatePipeline, Runnable renderPipeline) {
        super(updatePipeline, renderPipeline);
        lastTime = System.nanoTime();
    }

    @Override
    public void run() {
        Window window = Window.getInstance();
        window.setTitle(launchProps.getTitle());
        window.resize(launchProps.getWidth(), launchProps.getHeight());
        windowId = window.getWindowId();
        window.show();
        initializeRenderingContext();
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