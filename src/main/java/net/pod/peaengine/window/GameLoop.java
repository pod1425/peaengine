package net.pod.peaengine.window;

import net.pod.peaengine.Engine;
import org.lwjgl.opengl.GL11;

public class GameLoop {
    private static Runnable updatePipeline;
    private static Runnable renderPipeline;
    private static double interval = 1_000_000_000.0 / Engine.tps;
    private static double delta = 0;
    private static long lastTime;
    private static long currentTime;
    private static boolean initialized = false;
    public static void init(Runnable updatePipeline, Runnable renderPipeline) {
        GameLoop.updatePipeline = updatePipeline;
        GameLoop.renderPipeline = renderPipeline;
        lastTime = System.nanoTime();
        initialized = true;
    }

    public static void tick() {
        if (!initialized) {
            return;
        }
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / interval;
        lastTime = currentTime;

        while (delta >= 1) {
            updatePipeline.run();
            delta--;
        }

        // TODO: implement FPS capping
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        renderPipeline.run();
    }
}