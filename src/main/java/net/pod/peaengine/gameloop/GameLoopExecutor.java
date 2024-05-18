package net.pod.peaengine.gameloop;

public abstract class GameLoopExecutor extends Thread {
    protected Runnable updatePipeline;
    protected Runnable renderPipeline;
    protected long currentTick;

    public GameLoopExecutor(Runnable updatePipeline, Runnable renderPipeline) {
        this.updatePipeline = updatePipeline;
        this.renderPipeline = renderPipeline;
        currentTick = 0;
    }

    @Override
    public abstract void run();

    public long getCurrentTick() {
        return currentTick;
    }
}
