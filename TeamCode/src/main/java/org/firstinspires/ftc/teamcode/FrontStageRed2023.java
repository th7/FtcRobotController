package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FrontStageRed2023", group="Autonomous")
public class FrontStageRed2023 extends BaseAutoOp2023 {
    @Override
    public void init() {
        super.init();
        compute.stateMachine = compute.frontStageRed();
    }
}
