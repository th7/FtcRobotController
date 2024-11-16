package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseAutoOp;
import org.firstinspires.ftc.teamcode.Plans;

@Autonomous(name="AutoOpDropOffAndDrive2024", group="Autonomous")
public class AutoOpDropOffAndDrive2024 extends BaseAutoOp {
    @Override
    public void init() {
        super.init();
        chosenPlan = Plans.autoOpDropOffAndDrive();
    }
}
