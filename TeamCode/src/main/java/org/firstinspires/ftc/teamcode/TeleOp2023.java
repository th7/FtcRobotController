package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp2023", group="TeleOp")
public class TeleOp2023 extends BaseOp2023 {
    @Override
    public Input collectInputs() {
        Input input = super.collectInputs();

        input.gameStickLeftX = gamepad1.left_stick_x;
        input.gameStickLeftY = gamepad1.left_stick_y;
        input.gameStickRightX = gamepad1.right_stick_x;
        input.gameStickRightY = gamepad1.right_stick_y;

        input.dPadUp = gamepad1.dpad_up;
        input.dPadDown = gamepad1.dpad_down;
        input.dPadLeft = gamepad1.dpad_left;
        input.dPadRight = gamepad1.dpad_right;
        input.triangle = gamepad1.triangle;
        input.cross = gamepad1.cross;
        input.circle = gamepad1.circle;
        input.rightTrigger = gamepad1.right_trigger;
        input.leftTrigger = gamepad1.left_trigger;
        input.rightBumper = gamepad1.right_bumper;
        input.leftBumper = gamepad1.left_bumper;

        return input;
    }

    @Override
    public Output compute() {
        Output output = new Output();

        output.armMotorPower = compute.arm();
        output.movement = compute.manualDrive();
        output.winchMotorPower = compute.winch();

        compute.manualClaw();
        output.topClawPosition = memory.topClawPosition;
        output.bottomClawPosition = memory.bottomClawPosition;

        if (compute.input.circle) {
            output.launcherPosition = 0.7d;
        }

        output.addTel("gameStickLeftX", compute.input.gameStickLeftX);
        output.addTel("gameStickLeftY", compute.input.gameStickLeftY);
        output.addTel("gameStickRightX", compute.input.gameStickRightX);
        output.addTel("gameStickRightY", compute.input.gameStickRightY);

        output.addTel("frontLeftPower", output.movement.frontLeftPower);
        output.addTel("frontRightPower", output.movement.frontRightPower);
        output.addTel("rearLeftPower", output.movement.rearLeftPower);
        output.addTel("rearRightPower", output.movement.rearRightPower);

        output.addTel("circle", compute.input.circle);
        output.addTel("launcherPosition", output.launcherPosition);

        output.addTel("leftTrigger", compute.input.leftTrigger);
        output.addTel("rightTrigger", compute.input.rightTrigger);
        output.addTel("leftBumper", compute.input.leftBumper);
        output.addTel("rightBumper", compute.input.rightBumper);
        output.addTel("topClawPosition", output.topClawPosition);
        output.addTel("bottomClawPosition", output.bottomClawPosition);

        return output;
    }
}
