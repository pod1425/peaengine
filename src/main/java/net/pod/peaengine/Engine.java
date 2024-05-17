package net.pod.peaengine;

import net.pod.peaengine.window.GameLoop;
import net.pod.peaengine.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.HashMap;
import java.util.Map;

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

    public static Window getMainWindow() {
        return windows.get(mainWindowId);
    }

    public static void closeAllWindows() {
        for (Window w : windows.values()) {
            w.close();
        }
    }
    //TODO: implement closing of single window by id
}
