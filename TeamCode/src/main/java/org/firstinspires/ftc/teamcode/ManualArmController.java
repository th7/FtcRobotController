package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ManualArmController implements Tickable {
    private final DcMotor armMotor1;
    private final DcMotor armMotor2;
    private final Gamepad gamepad;
    private final Telemetry telemetry;

    public ManualArmController(DcMotor armMotor1, DcMotor armMotor2, Gamepad gamepad, Telemetry telemetry) {
        this.armMotor1 = armMotor1;
        this.armMotor2 = armMotor2;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void tick() {
        float power = armPower();
        armMotor1.setPower(power);
        armMotor2.setPower(power);
        telemetry.addData("armMotorPower", power);
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
