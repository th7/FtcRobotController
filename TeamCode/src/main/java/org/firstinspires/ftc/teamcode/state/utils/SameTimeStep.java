package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.MoveData;

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
}
