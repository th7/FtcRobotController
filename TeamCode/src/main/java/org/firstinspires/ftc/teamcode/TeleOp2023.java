package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp2023", group="TeleOp")
public class TeleOp2023 extends BaseOp2023 {
    ManualArmController armController;
    ManualMoveController moveController;
    ManualClawController clawController;
    ManualWinchController winchController;
    ManualDroneController droneController;

    @Override
    public void init() {
        super.init();
        this.armController = new ManualArmController(
                armMotor1, armMotor2, gamepad1, telemetry);
        this.moveController =  new ManualMoveController(
                leftFront, rightFront, leftBack, rightBack, gamepad1, telemetry);
        this.clawController = new ManualClawController(
                topClaw, bottomClaw, gamepad1, telemetry);
        this.winchController = new ManualWinchController(
                winchMotor, gamepad1, telemetry);
        this.droneController = new ManualDroneController(
                droneLauncher, gamepad1, telemetry);
    }

    @Override
    public void loop() {
        armController.tick();
        moveController.tick();
        clawController.tick();
        winchController.tick();
        droneController.tick();

        telemetry.addData("gameStickLeftX", gamepad1.left_stick_x);
        telemetry.addData("gameStickLeftY", gamepad1.left_stick_y);
        telemetry.addData("gameStickRightX", gamepad1.right_stick_x);
        telemetry.addData("gameStickRightY", gamepad1.right_stick_y);
        telemetry.addData("circle", gamepad1.circle);
        telemetry.addData("leftTrigger", gamepad1.left_trigger);
        telemetry.addData("rightTrigger", gamepad1.right_trigger);
        telemetry.addData("leftBumper", gamepad1.left_bumper);
        telemetry.addData("rightBumper", gamepad1.right_bumper);
        telemetry.update();
    }
}
