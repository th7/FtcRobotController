package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.MoveData;

public class Step implements PlanPart {
    private boolean started = false;
    private final VoidCallable start;
    private final Callable<Boolean> done;

    public Step(VoidCallable start, Callable<Boolean> done) {
        this.start = start;
        this.done = done;
    }

    public boolean done() {
        if (!started) { start.call(); started = true; }

        return this.done.call();
    }
}
