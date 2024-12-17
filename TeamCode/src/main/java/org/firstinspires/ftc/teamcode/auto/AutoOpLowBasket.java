package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseAutoOp;
import org.firstinspires.ftc.teamcode.Plans;

@Autonomous(name="AutoOpLowBasket", group="Autonomous")
public class AutoOpLowBasket extends BaseAutoOp {
    @Override
    public void init() {
        super.init();
        chosenPlan = Plans.autoOpLowBasket();
    }
}
