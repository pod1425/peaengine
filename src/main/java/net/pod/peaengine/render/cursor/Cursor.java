package net.pod.peaengine.render.cursor;

import net.pod.peaengine.physics.Vector2D;
import net.pod.peaengine.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public class Cursor {
    private final int width;
    private final int height;
    private final int xHotspot;
    private final int yHotspot;
    private final long cursor;

    /**
     * Creates a GLFW Cursor object
     * @param resourceName the texture image in resources/assets/textures/cursors
     * @param xHotspot How far to the right of the image the sweetspot is
     * @param yHotspot How far down on the image the sweetspot is
     * @throws Exception When unable to allocate memory
     */
    public Cursor(String resourceName, int xHotspot, int yHotspot) throws Exception {
        URL url = Cursor.class.getResource("/assets/textures/cursors/" + resourceName);
        BufferedImage image = ImageIO.read(url.openStream());

        this.xHotspot = xHotspot;
        this.yHotspot = yHotspot;
        this.width = image.getWidth();
        this.height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0,width);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            ByteBuffer buffer = stack.malloc(width * height * 4);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF)); //red
                    buffer.put((byte) ((pixel >> 8) & 0xFF)); //green
                    buffer.put((byte) (pixel & 0xFF)); //blue
                    buffer.put((byte) ((pixel >> 24) & 0xFF)); //alpha
                }
            }
            buffer.flip();

            GLFWImage glfwImage = GLFWImage.create();
            glfwImage.width(width);
            glfwImage.height(height);
            glfwImage.pixels(buffer);

            this.cursor = GLFW.glfwCreateCursor(glfwImage, xHotspot, yHotspot);
        }

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Get the offset of where clicks occur in the cursor
     * @return Vector2D representing the X and Y offset
     */
    public Vector2D getHotspot() {
        return new Vector2D(xHotspot, yHotspot);
    }

    public long getCursor() {
        return cursor;
    }
}
