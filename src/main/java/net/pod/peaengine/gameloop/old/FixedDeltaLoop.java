package net.pod.peaengine.gameloop.old;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoopExecutor;
import org.lwjgl.glfw.GLFW;

public class FixedDeltaLoop extends GameLoopExecutor {
    private static final long SECOND = 1_000_000_000;
    private double interval = ((double) SECOND) / Engine.tps;
    private double frameInterval = ((double) SECOND) / Engine.framerateCap;
    private double delta = 0;
    private double frameDelta = 0;
    private long lastTime;
    private long lastFrameTime;
    private long currentTime;

    private int fps;
    private int frameCount;
    private long lastFrameCheck;

    public FixedDeltaLoop(Runnable runBefore, Runnable updatePipeline, Runnable renderPipeline) {
        super(runBefore, updatePipeline, renderPipeline);
        lastTime = System.nanoTime();
        lastFrameTime = lastTime;
        fps = 0;
        frameCount = 0;
    }

    @Override
    public void run() {
        initializeRenderingContext();
        runBefore.run();

        GLFW.glfwSwapInterval(0); // v-sync
        while (Engine.shouldRun) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            while (delta >= 1) {
                updatePipeline.run();
                delta--;
                currentTick++;
            }

            currentTime = System.nanoTime();
            frameDelta += (currentTime - lastFrameTime) / frameInterval;
            lastFrameTime = currentTime;
            while (frameDelta >= 1) {
                beforeRender();
                renderPipeline.run();
                afterRender();
                frameDelta--;
                frameCount++;
            }

            if (currentTime - lastFrameCheck >= SECOND) {
                fps = frameCount;
                frameCount = 0;
                lastFrameCheck += SECOND;
            }

        }
    }

    @Override
    public int getCurrentFps() {
        return fps;
    }
}
