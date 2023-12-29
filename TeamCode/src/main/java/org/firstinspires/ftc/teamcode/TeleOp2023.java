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
        return compute.teleOp();
    }
}
