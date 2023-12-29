package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

import java.util.ArrayList;

public class Plan implements PlanPart {
    private final ArrayList<PlanPart> states = new ArrayList<>();
    private int currentStateIndex = 0;

    public Plan(PlanPart... all) {
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
            PlanPart currentState = states.get(currentStateIndex);
            if (currentState.done()) {
                currentStateIndex += 1;
            }
            return false;
        } catch(IndexOutOfBoundsException e) {
            return true;
        }
    }

    public Output.Movement movement() {
        try {
            PlanPart currentState = states.get(currentStateIndex);
            return currentState.movement();
        } catch(IndexOutOfBoundsException e) {
            return new Output.Movement();
        }
    }
}
