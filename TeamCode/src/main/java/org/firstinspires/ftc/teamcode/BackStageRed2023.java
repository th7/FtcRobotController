package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BackStageRed2023", group="Autonomous")
public class BackStageRed2023 extends BaseAutoOp2023 {
    @Override
    protected Output compute(Compute compute) {
        return compute.backstageRed();
    }
}

