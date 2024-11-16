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
        if (gamepad1.triangle) { Arm.liftUp(); } else if (gamepad1.square) { Arm.liftDown(); }
        if (gamepad1.circle) { Arm.winchUp(); } else if (gamepad1.x) { Arm.winchDown(); } else { Arm.winchStop(); }

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
        telemetry.update();
    }
}
