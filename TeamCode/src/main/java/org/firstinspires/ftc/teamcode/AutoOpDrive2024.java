package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoOpDrive2024", group="Autonomous")
public class AutoOpDrive2024 extends BaseAutoOp2023 {
    @Override
    public void init() {
        super.init();
        chosenPlan = plans.autoOpDrive();
    }

}
