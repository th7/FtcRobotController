package org.firstinspires.ftc.teamcode.state.utils;

import java.util.ArrayList;

public class LinearPlan implements PlanPart {
    private final ArrayList<PlanPart> planParts = new ArrayList<>();
    private int currentPlanPartIndex = 0;

    public LinearPlan(PlanPart... all) {
        addAll(all);
    }

    @Override
    public boolean done() {
        try {
            PlanPart currentState = planParts.get(currentPlanPartIndex);
            if (currentState.done()) {
                currentPlanPartIndex += 1;
            }
            return false;
        } catch(IndexOutOfBoundsException e) {
            return true;
        }
    }

    private void add(PlanPart planPart) {
        planParts.add(planPart);
    }

    private void addAll(PlanPart... all) {
        for (PlanPart planPart : all) {
            add(planPart);
        }
    }
}
