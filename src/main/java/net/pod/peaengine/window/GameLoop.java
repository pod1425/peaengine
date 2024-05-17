package net.pod.peaengine.window;

import net.pod.peaengine.Engine;
import org.lwjgl.opengl.GL11;

public class GameLoop {

    private static GameLoopExecutor executor = null;

    public static void launch(Runnable updatePipeline, Runnable renderPipeline) {
        executor = new GameLoopExecutor(updatePipeline, renderPipeline);
        executor.start();
    }

    private static class GameLoopExecutor extends Thread {
        private Runnable updatePipeline;
        private Runnable renderPipeline;
        private double interval = 1_000_000_000.0 / Engine.tps;
        private double delta = 0;
        private long lastTime;
        private long currentTime;

        public GameLoopExecutor(Runnable updatePipeline, Runnable renderPipeline) {
            this.updatePipeline = updatePipeline;
            this.renderPipeline = renderPipeline;
            lastTime = System.nanoTime();
        }

        @Override
        public void run() {
            while (Engine.shouldRun) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / interval;
                lastTime = currentTime;
                while (delta >= 1) {
                    tick();
                    delta--;
                }
            }
        }

        public void tick() {
            updatePipeline.run();
            // TODO: implement FPS capping
            renderPipeline.run();

        }
    }
}