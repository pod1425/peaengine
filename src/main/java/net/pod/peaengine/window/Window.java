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
    private static Window instance = null;

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }

    private Window() {
        title = "Game";
        width = 800;
        height = 600;
        init();
    }

    public long getWindowId() {
        return window;
    }

    // TODO: replace this placeholder when event system is in place
    public void renderCalled() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    /**
     * Initializes current window
     */
    private void init() {

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

        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void show() {
        GLFW.glfwShowWindow(window);
    }

    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
        GLFW.glfwSetWindowSize(window, width, height);
    }

    public void setFullscreen() {
        long monitor = GLFW.glfwGetPrimaryMonitor();
        GLFWVidMode mode = GLFW.glfwGetVideoMode(monitor);
        width = mode.width();
        height = mode.height();

        GLFW.glfwSetWindowMonitor(window, monitor, 0, 0,
                width, height, mode.refreshRate());
    }

    public void setTitle(String newTitle) {
        title = newTitle;
        GLFW.glfwSetWindowTitle(window, title);
    }


    @Override
    public void close() {
        keyCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free(); // oh, fuck off intellij
    }
}
