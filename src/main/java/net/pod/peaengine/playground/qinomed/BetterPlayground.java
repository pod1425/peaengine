package net.pod.peaengine.playground.qinomed;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.registry.builtin.Registries;
import net.pod.peaengine.window.WindowProps;

public class BetterPlayground {
    public static float posX = -0.5f;
    public static float posY = -0.5f;

    public static void main(String[] args) throws InterruptedException {
        Engine.init();
        GameLoop.launch(
                WindowProps.createNew().ofTitle("Hello").ofSize(800, 600),
                () -> {
                    Registries.textureRegistry.put("test","/assets/textures/test.png");
                },
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
                    Registries.textureRegistry.get("test").draw(posX, posY, 0.0125f);
                }
        );
        // wait 5 seconds before closing the game
        Thread.sleep(5000);
        // shutdown engine first (stops the game loop)
        Engine.shutdown();
    }
}
