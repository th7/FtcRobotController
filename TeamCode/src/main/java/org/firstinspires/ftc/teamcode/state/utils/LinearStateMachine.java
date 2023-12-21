package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

import java.util.ArrayList;

public class LinearStateMachine implements Stateful {
    private final ArrayList<Stateful> states = new ArrayList<>();
    private int currentStateIndex = 0;

    public LinearStateMachine(Stateful... all) {
        addAll(all);
    }
    public void add(Stateful stateful) {
        states.add(stateful);
    }

    public void addAll(Stateful... all) {
        for (Stateful stateful : all) {
            add(stateful);
        }
    }

    @Override
    public boolean done() {
        try {
            Stateful currentState = states.get(currentStateIndex);
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
            Stateful currentState = states.get(currentStateIndex);
            return currentState.movement();
        } catch(IndexOutOfBoundsException e) {
            return new Output.Movement();
        }
    }
}
