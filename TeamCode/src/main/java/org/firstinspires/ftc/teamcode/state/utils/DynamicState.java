package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

public class DynamicState implements Stateful {
    private final Callable<Stateful> statePicker;
    private Stateful pickedState;

    public DynamicState(Callable<Stateful> statePicker) {
        this.statePicker = statePicker;
    }

    public boolean done() {
        return getPickedState().done();
    }

    public Output.Movement movement() {
        return getPickedState().movement();
    }

    private Stateful getPickedState() {
        if (pickedState == null) { pickedState = statePicker.call(); }
        return pickedState;
    }
}
