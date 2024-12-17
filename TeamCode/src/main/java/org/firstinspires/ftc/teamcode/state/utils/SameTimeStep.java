package org.firstinspires.ftc.teamcode.state.utils;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public String currentStep() {
        List<String> currentSteps = (List<String>) Arrays.stream(parts).map(part -> part.currentStep());
        return String.join("/", currentSteps);
    }
}
