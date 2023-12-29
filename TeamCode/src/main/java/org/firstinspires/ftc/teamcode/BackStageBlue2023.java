package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BackStageBlue2023", group="Autonomous")
public class BackStageBlue2023 extends BaseAutoOp2023 {
    @Override
    public void init() {
        super.init();
        compute.stateMachine = compute.backStageBlue();
    }
}

