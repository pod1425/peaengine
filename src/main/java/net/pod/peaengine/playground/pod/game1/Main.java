package net.pod.peaengine.playground.pod.game1;

import net.pod.peaengine.Engine;
import net.pod.peaengine.gameloop.GameLoop;
import net.pod.peaengine.physics.GameObject;
import net.pod.peaengine.registry.builtin.Registries;
import net.pod.peaengine.window.WindowProps;

import java.util.HashSet;
import java.util.Set;

public class Main {
    GameObject player = new GameObject("Player");
    Set<GameObject> projectiles = new HashSet<>();
    GameObject target = new GameObject("Target");


    public static void main(String[] args) {
        Engine.init();


        GameLoop.launch(
                WindowProps.createNew().ofTitle("Hello").ofSize(800, 600),
                () -> {
                    Registries.textureRegistry.put("test","/assets/textures/test.png");
                },
                // update pipeline
                () -> {
                    long tick = GameLoop.getCurrentTick();

                },
                // render pipeline
                () -> {
                    long tick = GameLoop.getCurrentTick();


                }
        );
    }
}
