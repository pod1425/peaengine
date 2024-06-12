package net.pod.peaengine.registry;

import java.util.HashMap;

public class Registry<T> {
    protected HashMap<String, T> map;

    protected Registry() {
        this.map = new HashMap<>();
    }

    public T put(String name, T item) {
        map.put(name, item);
        return item;
    }

    public T get(String name) {
        return map.get(name);
    }
}
