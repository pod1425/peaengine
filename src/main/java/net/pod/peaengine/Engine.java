package net.pod.peaengine;

import net.pod.peaengine.event.EventListenerNotifier;
import net.pod.peaengine.render.texture.TextureLoader;
import net.pod.peaengine.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Engine {
    // unused for now
    public static int framerateCap = 60;
    // unused for now
    public static int tps = 20;
    private static long mainWindowId;
    public static boolean shouldRun;
    public static TextureLoader textureLoader;

    public static void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        textureLoader = new TextureLoader();
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

    /**
     * Should be called right at the end of program execution
     */
    public static void shutdown() {
        shouldRun = false;
        EventListenerNotifier.dispose();
        Window.getInstance().close();
    }

    public static long getMainWindow() {
        return mainWindowId;
    }

    public static void loadResources() {
        // TODO: add support for other resource types in the future
        String texturesPath = "/assets/textures/";

        try {
            URI uri = Engine.class.getResource(texturesPath).toURI();
            Path dirPath = Paths.get(uri);
            try (Stream<Path> stream = Files.list(dirPath)) {
                stream.forEach(path -> textureLoader.loadTexture(texturesPath + path.getFileName()));
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
