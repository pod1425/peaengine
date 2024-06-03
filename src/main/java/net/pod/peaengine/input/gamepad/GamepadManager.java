package net.pod.peaengine.input.gamepad;

import net.pod.peaengine.input.keyboard.Keys;

import java.lang.reflect.Field;
import java.util.HashMap;

public class GamepadManager {
    private static final HashMap<Integer, Boolean> buttons = new HashMap<>();

    static {
        //Adding keys to hashmap
        Field[] fields = Gamepad.class.getDeclaredFields();
        Gamepad gamepadObj = new Gamepad();
        for (Field field : fields) {
            try {
                buttons.put((Integer) field.get(gamepadObj), false);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
