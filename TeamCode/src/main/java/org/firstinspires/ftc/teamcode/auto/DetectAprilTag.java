package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseAutoOp;
import org.firstinspires.ftc.teamcode.Plans;

@Autonomous(name="DetectAprilTag", group="Autonomous")
public class DetectAprilTag extends BaseAutoOp {
    @Override
    public void init() {
        super.init();
        chosenPlan = Plans.detectAprilTag();
    }
}