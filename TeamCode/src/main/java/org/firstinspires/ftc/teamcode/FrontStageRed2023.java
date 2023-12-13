package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FrontStageRed2023", group="Autonomous")
public class FrontStageRed2023 extends BaseAutoOp2023 {
    @Override
    protected void setupCompute() {
        compute.stateMachine = compute.derpFrontStageRed();
    }
}
