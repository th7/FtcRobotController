package org.firstinspires.ftc.teamcode.state.utils;

import java.util.ArrayList;

public class LinearPlan implements PlanPart {
    private final ArrayList<PlanPart> planParts = new ArrayList<>();
    private int currentPlanPartIndex = 0;

    public LinearPlan(PlanPart... all) {
        addAll(all);
    }
    public void add(PlanPart planPart) {
        planParts.add(planPart);
    }

    public void addAll(PlanPart... all) {
        for (PlanPart planPart : all) {
            add(planPart);
        }
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
}
