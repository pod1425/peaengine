package net.pod.peaengine.playground.pod;

import net.pod.peaengine.event.EventArgs;
import net.pod.peaengine.event.EventListener;
import net.pod.peaengine.event.EventListenerNotifier;
import net.pod.peaengine.event.EventSource;

public class EventTest {
    public static class Button implements EventSource<EventArgs> {
        private String name;
        private EventListenerNotifier<EventArgs> notifier = new EventListenerNotifier<>();

        public Button(String name) {
            this.name = name;
        }

        @Override
        public void addEventListener(EventListener<EventArgs> listener) {
            notifier.addEventListener(listener);
        }

        @Override
        public void removeEventListener(EventListener<EventArgs> listener) {
            notifier.removeEventListener(listener);
        }

        public void click() {
            notifier.notifyListeners(new EventArgs(this, "Button " + name + " pressed!!!"));
        }

        public String getName() {
            return name;
        }
    }

    public static class Enemy implements EventListener<EventArgs> {
        private String name;

        public Enemy(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void sound() {
            System.out.println("Enemy " + name + ": grrrr!");
        }

        @Override
        public void handleEvent(EventArgs args) {
            sound();
        }
    }

    public static void pressButton(Button button) {
        button.click();
    }

    public static void main(String[] args) {
        // this is typically done inside Engine.init()
        EventListenerNotifier.init();

        Button button = new Button("Jacob");
        Enemy enemy1 = new Enemy("Uruk");
        Enemy enemy2 = new Enemy("Orok");
        Enemy enemy3 = new Enemy("Arak");

        button.addEventListener(enemy1);
        button.addEventListener(enemy2);
        button.addEventListener(enemy3);

        // you can pass the object wherever you want
        pressButton(button);
        // if you dont shutdown it, the app doesnt finish since there are still some threads running
        // its done in Engine class tho
        EventListenerNotifier.dispose();
        /*
        if you just do:
        System.exit(0);
        then the threads will finish improperly, and their work will be half-done,
        which may create resource leaks and potential data corruption
         */
    }
}
