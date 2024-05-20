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
    private static long mainWindowId;
    public static boolean shouldRun;

    public static void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        EventListenerNotifier.init();
        shouldRun = true;
    }

    public static long initializeNewMainWindow(String title, int width, int height) {
        Window window = Window.getInstance();
        window.setTitle(title);
        window.resize(width, height);
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

    public static long getMainWindow() {
        return mainWindowId;
    }

    public static void closeWindow() {
        Window.getInstance().close();
    }
}
