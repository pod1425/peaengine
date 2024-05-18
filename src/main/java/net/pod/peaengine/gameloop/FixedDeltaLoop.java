package net.pod.peaengine.gameloop;

import net.pod.peaengine.Engine;

public class FixedDeltaLoop extends GameLoopExecutor {
    private double interval = 1_000_000_000.0 / Engine.tps;
    private double delta = 0;
    private long lastTime;
    private long currentTime;

    public FixedDeltaLoop(Runnable updatePipeline, Runnable renderPipeline) {
        super(updatePipeline, renderPipeline);
        lastTime = System.nanoTime();
    }

    @Override
    public void run() {
        while (Engine.shouldRun) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            while (delta >= 1) {
                updatePipeline.run();
                // TODO: implement FPS capping
                renderPipeline.run();
                delta--;
                currentTick++;
            }
        }
    }
}
