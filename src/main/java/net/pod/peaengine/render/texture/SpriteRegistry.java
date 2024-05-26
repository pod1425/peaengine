package net.pod.peaengine.render.texture;

import java.util.HashMap;

public class SpriteRegistry {
    private static HashMap<String, Sprite> sprites = new HashMap<>();

    public static Sprite addSprite(String name, Sprite sprite) {
        sprites.put(name, sprite);
        return sprite;
    }
}
