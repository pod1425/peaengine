package net.pod.peaengine.window;
import net.pod.peaengine.Engine;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.Closeable;
import java.nio.IntBuffer;

public class Window implements AutoCloseable {
    private long window;
    private GLFWKeyCallback keyCallback;
    private String title;
    private int width;
    private int height;
    private Runnable updatePipeline;
    private Runnable renderPipeline;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        init();
    }

    public long getWindowId() {
        return window;
    }

    public void launchGameLoop(Runnable updatePipeline, Runnable renderPipeline) {
        this.updatePipeline = updatePipeline;
        this.renderPipeline = renderPipeline;
        loop();
    }

    private void loop() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close the window or has pressed the ESCAPE key.
        while (!GLFW.glfwWindowShouldClose(window)) {
            //TODO: outsource this all to GameLoop class

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
        }
    }

    private void init() {
        Engine.init();

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // hidden
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // non-resizable (for now)

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                    GLFW.glfwSetWindowShouldClose(window, true);
                }
            }
        };
        GLFW.glfwSetKeyCallback(window, keyCallback);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            // Center the window
            GLFW.glfwSetWindowPos(window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // v-sync
    }

    public void show() {
        GLFW.glfwShowWindow(window);
    }

    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
        GLFW.glfwSetWindowSize(window, width, height);
    }


    @Override
    public void close() {
        keyCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free(); // oh, fuck off intellij
    }
}
