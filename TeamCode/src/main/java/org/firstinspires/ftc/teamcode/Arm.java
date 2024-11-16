package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private static DcMotor liftMotor;
    private static DcMotor winchMotor;
    private static Telemetry telemetry;
    private static final float decreaseLiftPowerPoint = 200;
    private static final int minLiftPosition = 0;
    private static final int maxLiftPosition = 500;
    private static Arm data;
    private int targetLiftPosition = 0;
    private float winchPower = 0f;

    public static void init() {
        liftMotor = Hardware.liftMotor;
        winchMotor = Hardware.winchMotor;
        telemetry = Hardware.telemetry;
        data = new Arm();
        telemetry.addData("Arm.init()", true);
    }

    public static void liftUp() {
        data.targetLiftPosition = maxLiftPosition;
    }

    public static void liftDown() {
        data.targetLiftPosition = minLiftPosition;
    }

    public static void winchUp() {
        data.winchPower = 0.25f;
    }

    public static void winchDown() {
        data.winchPower = -0.25f;
    }

    public static void winchStop() {
        data.winchPower = 0f;
    }
    public static void tick() {
        float liftPower = liftPower();
        liftMotor.setPower(liftPower);
        winchMotor.setPower(data.winchPower);
        telemetry.addData("targetLiftPosition", data.targetLiftPosition);
        telemetry.addData("currentLiftPosition", liftMotor.getCurrentPosition());
        telemetry.addData("liftPower", liftPower);
        telemetry.addData("winchPower", data.winchPower);
    }

    private static float clip(float clipNumber) {
        if (clipNumber > 1) {return 1;}
        if (clipNumber < -1) {return -1;}
        return clipNumber;
    }
    private static float liftPower() {
        float error = data.targetLiftPosition - liftMotor.getCurrentPosition();
        float roughPower = 1 / decreaseLiftPowerPoint * error;
        return clip(roughPower);
    }
}
