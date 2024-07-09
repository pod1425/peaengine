package net.pod.peaengine.registry.builtin;

import net.pod.peaengine.registry.Registry;
import net.pod.peaengine.render.cursor.Cursor;

public class CursorRegistry extends Registry<Cursor> {
    public Cursor put(String name, String path, int xHotspot, int yHotspot) {
        try {
            Cursor cursor = new Cursor(path, xHotspot, yHotspot);
            this.map.put(name, cursor);
            return cursor;
        } catch (Exception e) {
            System.out.println("Unable to create cursor.");
            e.printStackTrace();
            return null;
        }
    }
}
