package net.pod.peaengine.playground.qinomed;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.physics.Vector2D;
import net.pod.peaengine.registry.builtin.Registries;
import net.pod.peaengine.window.WindowProps;

public class BetterPlayground {
    public static Vector2D pos = new Vector2D(-1, -1);

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
                    if (pos.x < 0.5f) {
                        pos.x += 0.01f;
                    }
                    if (pos.y < 0.5f) {
                        pos.y += 0.01f;
                    }
                },
                // render pipeline
                () -> {
                    long tick = GameLoop.getCurrentTick();
                    if (tick % 60 == 0) {
                        System.out.println("Render! " + GameLoop.getFps() + " FPS");
                    }
                    Registries.textureRegistry.get("test").draw(pos, 0.0125f);
                }
        );
        // wait 5 seconds before closing the game
        Thread.sleep(5000);
        // shutdown engine first (stops the game loop)
        Engine.shutdown();
    }
}
