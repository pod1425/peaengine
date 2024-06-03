package net.pod.peaengine.input.mouse;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MouseManager {
    private static final HashMap<Integer, Boolean> mouseBtns = new HashMap<>();

    static {
        //Adding mouse buttons to hashmap
        Field[] fields = Mouse.class.getDeclaredFields();
        Mouse mouseObj = new Mouse();
        for (Field field : fields) {
            try {
                mouseBtns.put((Integer) field.get(mouseObj), false);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static boolean getPressed(int btn) {
        return mouseBtns.get(btn);
    }

    public static void setBtn(int btn, boolean pressed) {
        mouseBtns.put(btn, pressed);
    }
}
