package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FrontStageRed2023", group="Autonomous")
public class FrontStageRed2023 extends BaseAutoOp2023 {
    @Override
    protected Output compute(Compute compute) {
        return compute.frontstageRed();
    }
}
