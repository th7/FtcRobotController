package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

public interface Stateful {
    boolean done();
    Output.Movement movement();

    Output compute();
}
