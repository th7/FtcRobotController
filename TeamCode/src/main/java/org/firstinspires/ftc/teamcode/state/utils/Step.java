package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

public class Step implements PlanPart {
    private boolean started = false;
    private final VoidCallable start;
    private final Callable<Boolean> done;
    private final Callable<Output.Movement> movement;

    public Step(VoidCallable start, Callable<Boolean> done) {
        this(start, Output.Movement::new, done);
    }

    public Step(VoidCallable start, Callable<Output.Movement> movement, Callable<Boolean> done) {
        this.start = start;
        this.movement = movement;
        this.done = done;
    }

    public boolean done() {
        if (!started) { start.call(); started = true; }

        return this.done.call();
    }

    public Output.Movement movement() {
        return movement.call();
    }
}
