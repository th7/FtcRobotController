package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoOpTeamProp2023", group="Autonomous")
public class AutoOpTeamProp2023 extends BaseAutoOp2023 {
    @Override
    protected void setupCompute() {
        compute.stateMachine = compute.derpTeamCode();
    }
}
