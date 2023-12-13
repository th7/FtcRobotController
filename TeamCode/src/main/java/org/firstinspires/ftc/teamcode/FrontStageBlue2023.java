package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FrontStageBlue2023", group="Autonomous")
public class FrontStageBlue2023 extends BaseAutoOp2023 {
    @Override
    protected Output compute(Compute compute) {
        return compute.frontstageBlue();
    }
}
