package net.pod.peaengine.gameloop;

import net.pod.peaengine.Engine;

public class DeltaTimeLoop extends GameLoopExecutor{
    private long lastTime;
    private long currentTime;

    public DeltaTimeLoop(Runnable updatePipeline, Runnable renderPipeline) {
        super(updatePipeline, renderPipeline);
        lastTime = System.nanoTime();
    }

    @Override
    public void run() {
        while (Engine.shouldRun) {
            currentTime = System.nanoTime();
            deltaTime = currentTime - lastTime;
            updatePipeline.run();
            renderPipeline.run();
            lastTime = currentTime;
        }
    }
}
