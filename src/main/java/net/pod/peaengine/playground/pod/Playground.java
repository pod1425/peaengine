package net.pod.peaengine.example;

import net.pod.peaengine.Engine;
import net.pod.peaengine.window.GameLoop;
import net.pod.peaengine.window.Window;
import org.lwjgl.opengl.GL11;

public class Playground {
    /*

     */
    public static void main(String[] args) throws InterruptedException {
        Engine.init();
        Engine.initializeNewMainWindow("Hello", 800, 600);
        Window mainWindow = Engine.getMainWindow();
        GameLoop.launch(
                // update pipeline
                () -> {
                    System.out.println("Update hehe");
                },
                // render pipeline
                () -> {

                    System.out.println("Render hehe");
                    mainWindow.renderCalled();
                }
        );
        Thread th = new Thread(() -> {
            try {
                Thread.sleep(5000); // wait 5 seconds before closing the game
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Engine.shouldRun = false;
            Engine.closeAllWindows();
        });
        th.start();

    }
}
