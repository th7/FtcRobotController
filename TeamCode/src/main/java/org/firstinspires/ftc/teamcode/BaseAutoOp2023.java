package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.state.utils.PlanPart;

public class BaseAutoOp2023 extends BaseOp2023 {
    public PlanPart chosenPlan;
    public Plans plans;

    @Override
    public void init() {
        super.init();
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        AutoArmController armController = new AutoArmController(armMotor1, armMotor2, telemetry);
        AutoMoveController moveController = new AutoMoveController(
                leftFront,
                rightFront,
                leftBack,
                rightBack,
                imu,
                telemetry);
        AutoClawController clawController = new AutoClawController(
                topClaw,
                bottomClaw,
                runtime,
                telemetry);
        plans = new Plans(
                compute,
                armController,
                moveController,
                clawController);
    }
    @Override
    public void loop() {
        plans.tick();
        telemetry.update();
    }
}
