package net.pod.peaengine.registry.builtin;

import net.pod.peaengine.registry.Registry;
import net.pod.peaengine.render.texture.Texture;
import net.pod.peaengine.render.texture.TextureLoader;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class TextureRegistry extends Registry<Texture> {
    protected TextureRegistry() {
        this.map = new HashMap<>();
    }

    public Texture put(String name, String filepath) {
        Texture tex = TextureLoader.getTexture(filepath,
                GL11.GL_TEXTURE_2D, // target
                GL11.GL_RGBA,     // dst pixel format
                GL11.GL_NEAREST, // min filter (unused)
                GL11.GL_NEAREST);

        map.put(name, tex);

        return tex;
    }
}
