package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp2023", group="TeleOp")
public class TeleOp2023 extends BaseOp2023 {
    @Override
    protected Output compute(Input input) {
        return compute.compute(input);
    }

    @Override
    protected void typeSpecificInit() {}
}
