package net.pod.peaengine.window;

import net.pod.peaengine.input.keyboard.KeyManager;
import net.pod.peaengine.input.mouse.Mouse;
import net.pod.peaengine.input.mouse.MouseManager;
import net.pod.peaengine.render.cursor.Cursor;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Window implements AutoCloseable {
    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback mousePosCallback;
    private GLFWScrollCallback mouseScrollCallback;
    private String title;
    private int width;
    private int height;
    private float aspectRatio;
    private static Window instance = null;
    private boolean cursorEnabled;

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
        cursorEnabled = true;
        aspectRatio = (float) width / height;
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
        if (GLFW.glfwRawMouseMotionSupported()) {
            GLFW.glfwSetInputMode(window, GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);
        }

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                System.out.println("Key press! " + key);
                if (action == GLFW.GLFW_PRESS) {
                    KeyManager.setKey(key, true);
                } else if (action == GLFW.GLFW_RELEASE) {
                    KeyManager.setKey(key, false);
                }
            }
        };
        GLFW.glfwSetKeyCallback(window, keyCallback);
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                System.out.println("Mouse event! " + button);
                if (action == GLFW.GLFW_PRESS) {
                    MouseManager.setBtn(button, true);
                } else if (action == GLFW.GLFW_RELEASE) {
                    MouseManager.setBtn(button, false);
                }
            }
        };
        GLFW.glfwSetMouseButtonCallback(window, mouseButtonCallback);
        mousePosCallback = new GLFWCursorPosCallback() {
            double prevX = 0;
            double prevY = 0;
            @Override
            public void invoke(long window, double xPos, double yPos) {
                MouseManager.deltaMovement.x = xPos - prevX;
                MouseManager.deltaMovement.y = prevY - yPos;
                prevY = yPos;
                prevX = xPos;
            }
        };
        GLFW.glfwSetCursorPosCallback(window, mousePosCallback);
        mouseScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double horScroll, double vertScroll) {
                if (horScroll != 0) {
                    boolean scrolledDown = horScroll < 0;
                    KeyManager.setKey(scrolledDown ? Mouse.SCROLL_DOWN : Mouse.SCROLL_UP,
                            scrolledDown);
                }
                if (vertScroll != 0) {
                    boolean scrolledDown = vertScroll < 0;
                    KeyManager.setKey(scrolledDown ? Mouse.SCROLL_DOWN : Mouse.SCROLL_UP,
                            scrolledDown);
                }
            }
        };
        GLFW.glfwSetScrollCallback(window, mouseScrollCallback);


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
        aspectRatio = (float) width / height;
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

    /**
     * Toggle the cursor between enabled and disabled. When disabled mouse movement will use raw mouse motion.
     */
    public void toggleCursor() {
        long window = getInstance().getWindowId();
        if (cursorEnabled) {
            GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
            GLFW.glfwSetInputMode(window, GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);
            cursorEnabled = false;
        } else {
            GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
            cursorEnabled = true;
        }
    }

    /**
     * Sets the current cursor being displayed
     * @param cursor Cursor stored in the cursorRegistry
     */
    public void setCursor(Cursor cursor) {
        GLFW.glfwSetCursor(Window.getInstance().getWindowId(), cursor.getCursor());
    }

    @Override
    public void close() {
        keyCallback.free();
        mouseButtonCallback.free();
        mousePosCallback.free();
        mouseScrollCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free(); // oh, fuck off intellij
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
}
