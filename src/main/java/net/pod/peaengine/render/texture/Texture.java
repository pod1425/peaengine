package net.pod.peaengine.render.texture;

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
