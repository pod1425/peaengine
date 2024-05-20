package net.pod.peaengine.gameloop;

import net.pod.peaengine.window.Window;
import net.pod.peaengine.window.WindowProps;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
public abstract class GameLoopExecutor extends Thread {
    protected Runnable updatePipeline;
    protected Runnable renderPipeline;
    protected long currentTick;
    protected long deltaTime;
    protected long windowId;
    protected WindowProps launchProps;

    public GameLoopExecutor(Runnable updatePipeline, Runnable renderPipeline) {
        this.updatePipeline = updatePipeline;
        this.renderPipeline = renderPipeline;
        currentTick = 0;
    }

    protected void initializeRenderingContext() {
        Window window = Window.getInstance();
        window.setTitle(launchProps.getTitle());
        window.resize(launchProps.getWidth(), launchProps.getHeight());
        windowId = window.getWindowId();
        window.show();
        GLFW.glfwMakeContextCurrent(windowId);
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    protected void beforeRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    protected void afterRender() {
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(windowId);
    }

    public abstract void run();

    public long getCurrentTick() {
        return currentTick;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public abstract int getCurrentFps();

    public void startLoop(WindowProps props) {
        this.launchProps = props;
        start();
    }
}