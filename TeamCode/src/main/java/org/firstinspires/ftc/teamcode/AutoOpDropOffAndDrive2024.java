package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoOpDropOffAndDrive2024", group="Autonomous")
public class AutoOpDropOffAndDrive2024 extends BaseAutoOp2023 {
    @Override
    public void init() {
        super.init();
        chosenPlan = plans.autoOpDropOffAndDrive();
    }

}
