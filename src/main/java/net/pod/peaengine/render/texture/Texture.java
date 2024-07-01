package net.pod.peaengine.render.texture;

import net.pod.peaengine.physics.Vector2D;
import net.pod.peaengine.window.Window;
import org.lwjgl.opengl.GL11;

public class Texture {
    private int target;
    private int textureID;
    private int height;
    private int width;
    private int texWidth;
    private int texHeight;
    private float widthRatio;
    private float heightRatio;

    public Texture(int target, int textureID) {
        this.target = target;
        this.textureID = textureID;
    }

    public void bind() {
        GL11.glBindTexture(target, textureID);
    }

    public void draw(Vector2D pos) {
        draw((float) pos.x, (float) pos.y, 1, 1);
    }

    public void draw(Vector2D pos, float scale) {
        draw((float) pos.x, (float) pos.y, scale, scale);
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
        this.bind();

        // translate to the right location and prepare to draw
        GL11.glTranslatef(x, y, 0);

        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, this.getHeight());
            GL11.glVertex2f(0, 0);

            GL11.glTexCoord2f(this.getWidth(), this.getHeight());
            GL11.glVertex2f(width * hScale / aspectRatio, 0);

            GL11.glTexCoord2f(this.getWidth(), 0);
            GL11.glVertex2f(width * hScale / aspectRatio, height * vScale);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, height * vScale);
        }
        GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }

    public float getWidth() {
        return widthRatio;
    }

    public float getHeight() {
        return heightRatio;
    }

    public int getImageWidth() {
        return width;
    }

    public int getImageHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
        setWidthRatio();
    }

    public void setHeight(int height) {
        this.height = height;
        setHeightRatio();
    }

    public void setTexWidth(int texWidth) {
        this.texWidth = texWidth;
        setWidthRatio();
    }

    public void setTexHeight(int texHeight) {
        this.texHeight = texHeight;
        setHeightRatio();
    }

    private void setHeightRatio() {
        if (texHeight != 0) {
            heightRatio = ((float) height) / texHeight;
        }
    }

    private void setWidthRatio() {
        if (texHeight != 0) {
            widthRatio = ((float) width) / texWidth;
        }
    }
}
