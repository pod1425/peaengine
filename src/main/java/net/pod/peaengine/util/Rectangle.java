package net.pod.peaengine.util;

public class Rectangle {
    private double x;
    private double y;
    private double width;
    private double height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Rectangle other) {
        return (other.getX() >= this.x &&
                other.getX() + other.getWidth() <= this.x + this.width &&
                other.getY() >= this.y &&
                other.getY() + other.getHeight() <= this.y + this.height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
