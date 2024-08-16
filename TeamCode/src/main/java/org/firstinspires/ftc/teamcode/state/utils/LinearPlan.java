package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

import java.util.ArrayList;

public class LinearPlan implements PlanPart {
    private final ArrayList<PlanPart> states = new ArrayList<>();
    private int currentPlanPartIndex = 0;

    public LinearPlan(PlanPart... all) {
        addAll(all);
    }
    public void add(PlanPart planPart) {
        states.add(planPart);
    }

    public void addAll(PlanPart... all) {
        for (PlanPart planPart : all) {
            add(planPart);
        }
    }

    @Override
    public boolean done() {
        try {
            PlanPart currentState = states.get(currentPlanPartIndex);
            if (currentState.done()) {
                currentPlanPartIndex += 1;
            }
            return false;
        } catch(IndexOutOfBoundsException e) {
            return true;
        }
    }

    public Output.Movement movement() {
        try {
            PlanPart currentState = states.get(currentPlanPartIndex);
            return currentState.movement();
        } catch(IndexOutOfBoundsException e) {
            return new Output.Movement();
        }
    }

    public int getPlanPartIndex() {
        return currentPlanPartIndex;
    }
}
