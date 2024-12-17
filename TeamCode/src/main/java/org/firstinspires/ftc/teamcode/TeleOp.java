package org.firstinspires.ftc.teamcode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="TeleOp")
public class TeleOp extends BaseOp {

    @Override
    public void init() {
        super.init();
        telemetry.addData("TeleOp.init()", true);
    }

    @Override
    public void loop() {
        if (gamepad1.triangle) { Arm.armToFloor(); } else if (gamepad1.x) { Arm.armToAboveFloor(); }
        if (gamepad1.circle) { Arm.armToLowBasket(); } else if (gamepad1.square) { Arm.armAway(); }
//        if (gamepad1.dpad_left) { Arm.wristOut(); } else if (gamepad1.dpad_right) { Arm.wristIn(); } else { Arm.wristStop(); }
        if (gamepad1.right_trigger > 0) { Arm.openClaw(); } else if (gamepad1.left_trigger > 0) { Arm.closeClaw(); }
        if (gamepad1.dpad_down) { Arm.manualDown(); } else if (gamepad1.dpad_up) { Arm.manualUp(); }

        Move.calculateManual(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        Arm.tick();
        Move.tick();
        setTelemetry();
    }

    private void setTelemetry() {
        telemetry.addData("gameStickLeftX", gamepad1.left_stick_x);
        telemetry.addData("gameStickLeftY", gamepad1.left_stick_y);
        telemetry.addData("gameStickRightX", gamepad1.right_stick_x);
        telemetry.addData("gameStickRightY", gamepad1.right_stick_y);
        telemetry.addData("circle", gamepad1.circle);
        telemetry.addData("leftTrigger", gamepad1.left_trigger);
        telemetry.addData("rightTrigger", gamepad1.right_trigger);
        telemetry.addData("leftBumper", gamepad1.left_bumper);
        telemetry.addData("rightBumper", gamepad1.right_bumper);
        telemetry.addData("dpad_up", gamepad1.dpad_up);
        telemetry.addData("dpad_down", gamepad1.dpad_down);
        telemetry.update();
    }
}
