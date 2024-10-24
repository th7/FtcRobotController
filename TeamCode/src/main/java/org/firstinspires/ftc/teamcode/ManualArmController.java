package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ManualArmController {
    private final DcMotor liftMotor;
    private final Gamepad gamepad;
    private final Telemetry telemetry;
    private final float decreaseLiftPowerPoint = 54;
    private final int minLiftPosition = 0;
    private final int maxLiftPosition = 10;
    private int targetLiftPosition = 0;

    public ManualArmController(DcMotor liftMotor, Gamepad gamepad, Telemetry telemetry) {
        this.liftMotor = liftMotor;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void tick() {
        setLiftTargetPosition();
        float liftPower = liftPower();
        liftMotor.setPower(liftPower);
        telemetry.addData("targetLiftPosition", targetLiftPosition);
        telemetry.addData("currentLiftPosition", liftMotor.getCurrentPosition());
        telemetry.addData("liftPower", liftPower);
    }

    private void setLiftTargetPosition() {
        if (gamepad.triangle) {
            targetLiftPosition = maxLiftPosition;
        } else if (gamepad.square) {
            targetLiftPosition = minLiftPosition;
        }
    }
    private float clip(float clipNumber) {
        if (clipNumber > 1) {return 1;}
        if (clipNumber < -1) {return -1;}
        return clipNumber;
    }
    private float liftPower() {
        float error = targetLiftPosition - liftMotor.getCurrentPosition();
        float roughPower = 1 / decreaseLiftPowerPoint * error;
        return clip(roughPower);
    }
    private float armPower() {
        if (gamepad.dpad_up) {
            return 0.25f;
        }

        if (gamepad.dpad_down) {
            return -0.25f;
        }

        return 0f;
    }
}
