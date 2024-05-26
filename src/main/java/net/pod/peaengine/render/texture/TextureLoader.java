package net.pod.peaengine.render.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

public class TextureLoader {
    private HashMap<String, Texture> textures = new HashMap<>();
    private ColorModel glAlphaColorModel;
    private ColorModel glColorModel;
    private IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

    public TextureLoader() {
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8, 8},
                true,
                false,
                ComponentColorModel.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);

        glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8, 0},
                false,
                false,
                ComponentColorModel.OPAQUE,
                DataBuffer.TYPE_BYTE);
    }

    private int createTextureID() {
        GL11.glGenTextures(textureIDBuffer);
        return textureIDBuffer.get(0);
    }

    public void loadTexture(String filepath) {
        Texture tex = getTexture(filepath,
                GL11.GL_TEXTURE_2D, // target
                GL11.GL_RGBA,     // dst pixel format
                GL11.GL_NEAREST, // min filter (unused)
                GL11.GL_NEAREST);

        textures.put(filepath, tex);
    }

    public Texture getTexture(String filepath) {
        Texture tex = textures.get(filepath);

        if (tex != null) {
            return tex;
        }

        tex = getTexture(filepath,
                GL11.GL_TEXTURE_2D, // target
                GL11.GL_RGBA,     // dst pixel format
                GL11.GL_NEAREST, // min filter (unused)
                GL11.GL_NEAREST);

        textures.put(filepath, tex);

        return tex;
    }

    public Texture getTexture(String resourceName,
                              int target,
                              int dstPixelFormat,
                              int minFilter,
                              int magFilter) {
        int srcPixelFormat;

        // create the texture ID for this texture
        int textureID = createTextureID();
        Texture texture = new Texture(target, textureID);

        // bind this texture
        GL11.glBindTexture(target, textureID);

        BufferedImage bufferedImage;

        try {
            bufferedImage = loadImage(resourceName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());

        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL11.GL_RGBA;
        } else {
            srcPixelFormat = GL11.GL_RGB;
        }

        // convert that image into a byte buffer of texture data
        ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);

        if (target == GL11.GL_TEXTURE_2D) {
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
        }

        // produce a texture from the byte buffer
        GL11.glTexImage2D(target,
                0,
                dstPixelFormat,
                get2Fold(bufferedImage.getWidth()),
                get2Fold(bufferedImage.getHeight()),
                0,
                srcPixelFormat,
                GL11.GL_UNSIGNED_BYTE,
                textureBuffer);

        return texture;
    }

    private static int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }

    private ByteBuffer convertImageData(BufferedImage bufferedImage, Texture texture) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        int texWidth = get2Fold(bufferedImage.getWidth());
        int texHeight = get2Fold(bufferedImage.getHeight());

        texture.setTexHeight(texHeight);
        texture.setTexWidth(texWidth);

        // create raster
        boolean hasAlpha = bufferedImage.getColorModel().hasAlpha();
        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, hasAlpha ? 4 : 3, null);
        texImage = new BufferedImage(hasAlpha ? glAlphaColorModel : glColorModel, raster, false, new Hashtable());

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, texWidth, texHeight);
        g.drawImage(bufferedImage, 0, 0, null);

        // build a byte buffer from the temporary image for opengl
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    private BufferedImage loadImage(String filepath) throws IOException {
        URL url = TextureLoader.class.getResource(filepath);

        if (url == null) {
            throw new IOException("Cannot find: " + filepath);
        }

        BufferedImage bufferedImage = ImageIO.read(url);

        return bufferedImage;
    }
}
