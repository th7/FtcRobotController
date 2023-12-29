package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

import java.util.Arrays;
import java.util.stream.Stream;

public class SameTimeStep implements PlanPart {
    private final PlanPart[] parts;

    public SameTimeStep(PlanPart ... parts) {
        this.parts = parts;
    }

    public boolean done() {
        boolean allDone = true;
        for (PlanPart part : parts) {
            if (!part.done()) { allDone = false; }
        }
        return allDone;
    }

    public Output.Movement movement() {
        return movement.call();
    }
}
