package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ManualMoveController {
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;
    private final Gamepad gamepad;
    private final Telemetry telemetry;

    public ManualMoveController(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, Gamepad gamepad, Telemetry telemetry) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void tick() {
        Output.Movement movement = calculatePower();
        leftFront.setPower(movement.frontLeftPower);
        rightFront.setPower(movement.frontRightPower);
        leftBack.setPower(movement.rearLeftPower);
        rightBack.setPower(movement.rearRightPower);
        telemetry.addData("frontLeftPower", movement.frontLeftPower);
        telemetry.addData("frontRightPower", movement.frontRightPower);
        telemetry.addData("rearLeftPower", movement.rearLeftPower);
        telemetry.addData("rearRightPower", movement.rearRightPower);
    }

    private Output.Movement calculatePower() {
        return manualTurn().add(0f, 1f, manualMove(), manualStrafe());
    }

    private Output.Movement manualTurn() {
        return Output.Movement.turn(gamepad.right_stick_x, 0f, 1f);
    }

    private Output.Movement manualMove() {
        return Output.Movement.move(-gamepad.left_stick_y, 0f, 1f);
    }

    private Output.Movement manualStrafe() {
        return Output.Movement.strafe(gamepad.left_stick_x, 0f, 1f);
    }
}
