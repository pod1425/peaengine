package net.pod.peaengine.render.texture;

import net.pod.peaengine.window.Window;
import org.lwjgl.opengl.GL11;

public class Sprite {
    private Texture texture;
    private int width;
    private int height;

    public Sprite(TextureLoader loader, String ref) {
        this.texture = loader.getTexture("/assets/textures/" + ref);
        this.width = texture.getImageWidth();
        this.height = texture.getImageHeight();
    }

    public Sprite(Texture texture) {
        this.texture = texture;
        this.width = texture.getImageWidth();
        this.height = texture.getImageHeight();
    }

    public int getWidth() {
        return texture.getImageWidth();
    }

    public int getHeight() {
        return texture.getImageHeight();
    }

    public void draw(float x, float y) {
        draw(x, y, 1, 1);
    }

    public void draw(float x, float y, float scale) {
        draw(x, y, scale, scale);
    }

    public void draw(float x, float y, float hScale, float vScale) {
        float aspectRatio = Window.getInstance().getAspectRatio();

        // store the current model matrix
        GL11.glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        GL11.glTranslatef(x, y, 0);

        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, texture.getHeight());
            GL11.glVertex2f(0, 0);

            GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
            GL11.glVertex2f(width * hScale / aspectRatio, 0);

            GL11.glTexCoord2f(texture.getWidth(), 0);
            GL11.glVertex2f(width * hScale / aspectRatio, height * vScale);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, height * vScale);
        }
        GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }
}
