package net.pod.peaengine.input.mouse;

import net.pod.peaengine.physics.Vector2D;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MouseManager {
    private static final HashMap<Integer, Boolean> mouseBtns = new HashMap<>();
    public static Vector2D deltaMovement;

    public static void init() {
        //Adding mouse buttons to hashmap
        deltaMovement = new Vector2D(0, 0);
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

    /**
     * Gets if a mouse button is currently pressed
     * @param button the mouse button to check, use Mouse class
     * @return true if pressed, false otherwise
     */
    public static boolean getPressed(int button) {
        return mouseBtns.get(button);
    }

    /**
     * Set a mouse button to pressed or released
     * @param btn the button that has been pressed
     * @param pressed true if pressed, false if released
     */
    public static void setBtn(int btn, boolean pressed) {
        mouseBtns.put(btn, pressed);
    }
}
