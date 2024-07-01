package net.pod.peaengine.playground.duck;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.input.keyboard.KeyManager;
import net.pod.peaengine.input.keyboard.Keys;
import net.pod.peaengine.input.mouse.MouseManager;
import net.pod.peaengine.window.Window;
import net.pod.peaengine.window.WindowProps;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


public class ControlsTest {

    public static float posX = -0.5f;
    public static float posY = -0.5f;

    public static void main(String[] args) throws InterruptedException {
        Engine.init();
        GameLoop.launch(
                WindowProps.createNew().ofTitle("Hello").ofSize(800, 600),
                () -> {
                    // sorry the code wouldn't compile if I didn't add it
                },
                // update pipeline
                () -> {
                    long tick = GameLoop.getCurrentTick();
                    if (tick % 60 == 0) {
                        System.out.println("Update!");
                    }
                    if (KeyManager.getPressed(Keys.W)) posY += 0.1f;
                    if (KeyManager.getPressed(Keys.S)) posY -= 0.1f;
                    if (KeyManager.getPressed(Keys.A)) posX -= 0.1f;
                    if (KeyManager.getPressed(Keys.D)) posX += 0.1f;
                    if (KeyManager.getPressed(Keys.ENTER)) Window.getInstance().toggleCursor();
                    posX += ((float) MouseManager.deltaMovement.x /1000);
                    posY += ((float) MouseManager.deltaMovement.y /1000);
                },
                // render pipeline
                () -> {
                    long tick = GameLoop.getCurrentTick();
                    if (tick % 60 == 0) {
                        System.out.println("Render! " + GameLoop.getFps() + " FPS");
                    }
                    drawSampleTriangle(posX, posY);
                }
        );
        // wait 5 seconds before closing the game
        Thread.sleep(60000);
        // shutdown engine first (stops the game loop)
        Engine.shutdown();
    }

    public static void drawSampleTriangle(float x, float y) {
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex2f(x - 0.05f, y - 0.05f);
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex2f(x + 0.05f, y - 0.05f);
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex2f(x + 0.0f, y + 0.05f);
        GL11.glEnd();
    }
}
