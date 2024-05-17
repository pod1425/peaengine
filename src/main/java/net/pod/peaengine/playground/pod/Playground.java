package net.pod.peaengine.playground.pod;

import net.pod.peaengine.Engine;
import net.pod.peaengine.window.GameLoop;
import net.pod.peaengine.window.Window;

public class Playground {

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

        Thread.sleep(5000); // wait 5 seconds before closing the game
        Engine.shouldRun = false;
        Engine.closeAllWindows();
    }
}