package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseAutoOp;
import org.firstinspires.ftc.teamcode.Plans;

@Autonomous(name="AutoOpDoubleLowBasket", group="Autonomous")
public class AutoOpDoubleLowBasket extends BaseAutoOp {
    @Override
    public void init() {
        super.init();
        chosenPlan = Plans.autoOpDoubleLowBasket();
    }
}
