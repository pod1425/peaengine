package net.pod.peaengine.playground.pod;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.gameloop.VariableDeltaLoop;
import net.pod.peaengine.window.Window;

public class Playground {

    public static void main(String[] args) throws InterruptedException {
        Engine.init();
        Engine.initializeNewMainWindow("Hello", 800, 600);
        GameLoop.setLoopType(VariableDeltaLoop.class);
        Window mainWindow = Engine.getMainWindow();
        GameLoop.launch(
                // update pipeline
                () -> {
                    System.out.println("Update hehe");
                },
                // render pipeline
                () -> {

                    System.out.println("Render hehe, " + (1_000_000_000 / GameLoop.getExecutor().getDeltaTime()) + " FPS");
                    mainWindow.renderCalled();
                }
        );

        Thread.sleep(5000); // wait 5 seconds before closing the game
        Engine.closeAllWindows();
        Engine.dispose();
    }
}
