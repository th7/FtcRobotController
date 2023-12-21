package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

public class State implements Stateful {
    private boolean started = false;
    private final VoidCallable start;
    private final Callable<Boolean> done;
    private final Callable<Output.Movement> movement;

    public State(VoidCallable start, Callable<Boolean> done) {
        this(start, Output.Movement::new, done);
    }

    public State(VoidCallable start, Callable<Output.Movement> movement, Callable<Boolean> done) {
        this.start = start;
        this.movement = movement;
        this.done = done;
    }

    public boolean done() {
        if (!started) { start.call(); started = true; }
        if (done.call()) {
            started = false;
            return true;
        }

        return false;
    }

    public Output.Movement movement() {
        return movement.call();
    }
}
