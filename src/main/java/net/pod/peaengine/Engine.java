package net.pod.peaengine;

import net.pod.peaengine.event.EventListenerNotifier;
import net.pod.peaengine.input.keyboard.KeyManager;
import net.pod.peaengine.input.mouse.MouseManager;
import net.pod.peaengine.physics.GameObject;
import net.pod.peaengine.window.Window;
import net.pod.peaengine.window.WindowProps;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.HashMap;
import java.util.Map;

public class Engine {
    // unused for now
    public static int framerateCap = 60;
    // unused for now
    public static int tps = 20;
    private static long mainWindowId;
    public static boolean shouldRun;
    public static final Map<Integer, GameObject> objects= new HashMap<>();

    public static void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        EventListenerNotifier.init();
        KeyManager.init();
        MouseManager.init();
        shouldRun = true;
    }

    public GameObject getObject(int id) {
        return objects.get(id);
    }

    public void addObject(GameObject o) {
        int key = objects.size();
        while (objects.containsKey(key)) {
            key++;
        }
        objects.put(key, o);
        o.id = key;
    }
    public void removeObject(int id) {
        GameObject o = objects.get(id);
        objects.remove(id);
        o.dispose();
    }

    public static long initializeNewMainWindow(WindowProps props) {
        Window window = Window.getInstance(props);
        mainWindowId = window.getWindowId();
        window.show();
        return mainWindowId;
    }

    /**
     * Should be called right at the end of program execution
     */
    public static void shutdown() {
        shouldRun = false;
        EventListenerNotifier.dispose();
        Window.getInstance(null).close();
    }

    public static long getMainWindow() {
        return mainWindowId;
    }
}
