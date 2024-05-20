package net.pod.peaengine.playground.pod;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.window.WindowProps;
import org.lwjgl.opengl.GL11;


public class Playground {

    public static float posX = -0.5f;
    public static float posY = -0.5f;

    public static void main(String[] args) throws InterruptedException {
        Engine.init();
        GameLoop.launch(
                WindowProps.createNew().ofTitle("Hello").ofSize(800, 600),
                // update pipeline
                () -> {
                    long tick = GameLoop.getCurrentTick();
                    if (tick % 60 == 0) {
                        System.out.println("Update!");
                    }
                    if (posX < 0.5f) {
                        posX += 0.01f;
                    }
                    if (posY < 0.5f) {
                        posY += 0.01f;
                    }
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
        Thread.sleep(5000);
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
