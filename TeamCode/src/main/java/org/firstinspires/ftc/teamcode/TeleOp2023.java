package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp2023", group="TeleOp")
public class TeleOp2023 extends BaseOp2023 {
    @Override
    protected Output compute(Compute compute) {
        return compute.teleOp();
    }

    @Override
    protected void setupCompute() {}

    @Override
    protected void typeSpecificInit() {}

}
