package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoOpDriveToAprilTag2023", group="Autonomous")
public class AutoOpDriveToAprilTag2023 extends BaseAutoOp2023 {
    @Override
    public void init() {
        super.init();
        chosenPlan = plans.aprilTag();
    }
}
