package net.pod.peaengine.input.keyboard;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeyManager {
    private static final HashMap<Integer, Boolean> keys = new HashMap<>();

    static {
        //Adding keys to hashmap
        Field[] fields = Keys.class.getDeclaredFields();
        Keys keyObj = new Keys();
        for (Field field : fields) {
            try {
                keys.put((Integer) field.get(keyObj), false);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static boolean getPressed(int key) {
        return keys.get(key);
    }

    public static void setKey(int key, boolean pressed) {
        keys.put(key, pressed);
    }

}
