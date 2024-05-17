package net.pod.peaengine.event;

/**
 * Represents event info
 */
public class EventArgs {
    private final Object sender;
    private final Object argument;

    public EventArgs(Object sender, Object argument) {
        this.sender = sender;
        this.argument = argument;
    }

    public EventArgs(Object sender) {
        this.sender = sender;
        this.argument = null;
    }

    public Object getSender() {
        return sender;
    }

    public Object getArgument() {
        return argument;
    }
}
