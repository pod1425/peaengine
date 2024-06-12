package net.pod.peaengine.input.keyboard;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeyManager {
    private static final HashMap<Integer, Boolean> keys = new HashMap<>();

    public static void init() {
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

    /**
     * Gets if a key on the keyboard is currently pressed
     * @param key the keycode of the pressed key, use Keys class
     * @return true if key is pressed down, false otherwise
     */
    public static boolean getPressed(int key) {
        return keys.get(key);
    }

    /**
     * Set a key to being pressed down or released
     * @param key keycode of the key to set
     * @param pressed whether the key is currently pressed down
     */
    public static void setKey(int key, boolean pressed) {
        keys.put(key, pressed);
    }

}
