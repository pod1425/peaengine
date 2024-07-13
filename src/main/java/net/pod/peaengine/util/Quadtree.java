package net.pod.peaengine.util;

import net.pod.peaengine.interract.Collideable;
import net.pod.peaengine.physics.Vector2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class Quadtree {

    private int MAX_OBJECTS = 4;
    private int MAX_LEVELS = 5;

    private int level;
    private List<Collideable> objects;
    private Rectangle bounds;
    private Quadtree[] nodes;

    public Quadtree(int level, Rectangle bounds) {
        this.level = level;
        this.objects = new ArrayList<>();
        this.bounds = bounds;
        this.nodes = new Quadtree[4];
    }

    public void clear() {
        objects.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    private void split() {
        int subWidth = (int) (bounds.getWidth() / 2);
        int subHeight = (int) (bounds.getHeight() / 2);
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();

        nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new Quadtree(level + 1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new Quadtree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    private int getIndex(Collideable collideable) {
        int index = -1;
        Vector2D position = collideable.getCollider().getMin();
        Vector2D maxPosition = collideable.getCollider().getMax();
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        boolean topQuadrant = (position.y < horizontalMidpoint && maxPosition.y < horizontalMidpoint);
        boolean bottomQuadrant = (position.y > horizontalMidpoint);

        if (position.x < verticalMidpoint && maxPosition.x < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            } else if (bottomQuadrant) {
                index = 2;
            }
        } else if (position.x > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            } else if (bottomQuadrant) {
                index = 3;
            }
        }

        return index;
    }

    private void grow(Rectangle newBounds) {
        int newWidth = (int) (Math.max(bounds.getWidth(), newBounds.getWidth()) * 2);
        int newHeight = (int) (Math.max(bounds.getHeight(), newBounds.getHeight()) * 2);
        int newX = (int) (Math.min(bounds.getX(), newBounds.getX()) - newWidth / 4);
        int newY = (int) (Math.min(bounds.getY(), newBounds.getY()) - newHeight / 4);

        Rectangle newRect = new Rectangle(newX, newY, newWidth, newHeight);

        Quadtree newRoot = new Quadtree(0, newRect);
        newRoot.nodes = this.nodes;
        newRoot.objects = this.objects;
        newRoot.split();

        this.bounds = newRect;
        this.nodes = newRoot.nodes;
        this.objects = newRoot.objects;

        for (Collideable object : newRoot.objects) {
            insert(object);
        }
    }

    public void insert(Collideable collideable) {
        Rectangle objectBounds = new Rectangle((int) collideable.getCollider().getMin().x,
                (int) collideable.getCollider().getMin().y,
                (int) (collideable.getCollider().getMax().x - collideable.getCollider().getMin().x),
                (int) (collideable.getCollider().getMax().y - collideable.getCollider().getMin().y));

        if (!bounds.contains(objectBounds)) {
            grow(objectBounds);
        }

        if (nodes[0] != null) {
            int index = getIndex(collideable);
            if (index != -1) {
                nodes[index].insert(collideable);
                return;
            }
        }

        objects.add(collideable);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public List<Collideable> retrieve(List<Collideable> returnObjects, Collideable collideable) {
        int index = getIndex(collideable);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, collideable);
        }

        returnObjects.addAll(objects);

        return returnObjects;
    }
}