package net.pod.peaengine.window;


// TODO: remake this into LoopLaunchArguments or something like that
public class WindowProps {
    private String title;
    private int width;
    private int height;

    public static final WindowProps DEFAULT = new WindowProps();

    private WindowProps() {
        title = "Game";
        width = 800;
        height = 600;
    }

    public static WindowProps createNew() {
        return new WindowProps();
    }

    public WindowProps ofTitle(String title) {
        this.title = title;
        return this;
    }
    public WindowProps ofWidth(int width) {
        this.width = width;
        return this;
    }
    public WindowProps ofHeight(int height) {
        this.height = height;
        return this;
    }
    public WindowProps ofSize(int width, int height) {
        this.height = height;
        this.width = width;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
