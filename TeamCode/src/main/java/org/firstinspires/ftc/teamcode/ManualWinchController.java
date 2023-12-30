package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ManualWinchController {
    private final DcMotor winchMotor;
    private final Gamepad gamepad;
    private final Telemetry telemetry;

    public ManualWinchController(DcMotor winchMotor, Gamepad gamepad, Telemetry telemetry) {
        this.winchMotor = winchMotor;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void tick() {
        float power = winchPower();
        winchMotor.setPower(power);
        telemetry.addData("winchPower", power);
    }

    private float winchPower() {
        if (gamepad.triangle) {
            return 1f;
        }

        if (gamepad.cross) {
            return -1f;
        }

        return 0f;
    }
}
