package net.pod.peaengine;

import net.pod.peaengine.event.EventListenerNotifier;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Engine {
    public static int framerateCap;
    public static int tps = 20;
    private static Map<Long, Window> windows; // no, not the OS
    public static long mainWindowId;
    public static boolean shouldRun;

    public static void init() {
        windows = new HashMap<>();
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        EventListenerNotifier.init();
        shouldRun = true;
    }

    public static long initializeNewMainWindow(String title, int width, int height) {
        Window window = new Window(title, width, height);
        windows.put(window.getWindowId(), window);
        mainWindowId = window.getWindowId();
        window.show();
        return mainWindowId;
    }
    //TODO: implement creation of other windows
    public static void launchGameLoop(Runnable updatePipeline, Runnable renderPipeline) {
        GameLoop.launch(updatePipeline, renderPipeline);

    }

    /**
     * Should be called right at the end of program execution
     */
    public static void dispose() {
        shouldRun = false;
        EventListenerNotifier.dispose();
    }

    public static Window getMainWindow() {
        return windows.get(mainWindowId);
    }

    public static void closeAllWindows() {
        for (Window w : windows.values()) {
            w.close();
        }
    }

    /**
     * Closes a window by id
     * @param id window id
     * @throws NullPointerException if window with that {@code id} is {@code null}
     */
    public static void closeWindow(long id) {
        Objects.requireNonNull(windows.get(id), "Window with ID: " + id + " does not exist!").close();
    }
}
