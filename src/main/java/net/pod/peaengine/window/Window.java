package net.pod.peaengine.window;

import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class Window implements AutoCloseable {
    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
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
                System.out.println("Key press!");
                //TODO key press events and shit
            }
        };
        GLFW.glfwSetKeyCallback(window, keyCallback);
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2) {
                System.out.println("Mouse event!");
                // TODO mouse press events and shit
            }
        };
        GLFW.glfwSetMouseButtonCallback(window, mouseButtonCallback);

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
        mouseButtonCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free(); // oh, fuck off intellij
    }
}
