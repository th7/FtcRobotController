package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.state.utils.PlanPart;

public abstract class BaseAutoOp extends BaseOp {
    public PlanPart chosenPlan;

    @Override
    public void init() {
        super.init();
        Hardware.initAuto();
        telemetry.addData("BaseAutoOp.init()", true);
    }
    @Override
    public void loop() {
        Move.calculateAuto();
        Move.tick();
        chosenPlan.done();
        telemetry.update();
    }
}
