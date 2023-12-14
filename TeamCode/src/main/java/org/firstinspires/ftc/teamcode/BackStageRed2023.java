package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BackStageRed2023", group="Autonomous")
public class BackStageRed2023 extends BaseAutoOp2023 {
    @Override
    protected void setupCompute() {
        compute.stateMachine = compute.backStageRed();
    }
}

