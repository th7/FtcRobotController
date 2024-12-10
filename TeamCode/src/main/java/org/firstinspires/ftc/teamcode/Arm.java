package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private static DcMotor armMotor;
    private static DcMotor winchMotor;
    private static DcMotor wristMotor;
    private static Servo leftClaw;
    private static Servo rightClaw;
    private static Telemetry telemetry;
    private static final float inverseArmGain = 100;
    private static final int minLiftPosition = 0;
    private static final int maxLiftPosition = 2700;
    private static final int manualArmRate = 50;
    private static Arm data;
    private int targetLiftPosition = 0;
    private float winchPower = 0f;
    private float wristPower = 0f;
    private float leftClawPosition = 0.5f;
    private float rightClawPosition = 0.5f;


    public static void init() {
        armMotor = Hardware.armMotor;
        rightClaw = Hardware.rightClaw;
        leftClaw = Hardware.leftClaw;
//        winchMotor = Hardware.winchMotor;
        wristMotor = Hardware.wristMotor;
        telemetry = Hardware.telemetry;
        data = new Arm();
        telemetry.addData("Arm.init()", true);
    }

    public static boolean done() {
        return Util.closeEnough(armMotor.getCurrentPosition(), data.targetLiftPosition, 5);
    }

    public static void manualUp() {
        data.targetLiftPosition = armMotor.getCurrentPosition() + manualArmRate;
    }

    public static void manualDown() {
        data.targetLiftPosition = armMotor.getCurrentPosition() - manualArmRate;
    }

    public static void armToFloor() { data.targetLiftPosition = maxLiftPosition; }

    public static void armAway() {
        data.targetLiftPosition = minLiftPosition;
    }

    public static void armToLowBasket() { data.targetLiftPosition = 1500; }

    public static void armToHighBasket() { data.targetLiftPosition = 350; }

    public static void armToAboveFloor () { data.targetLiftPosition = 100; }

    public static void winchUp() {
        data.winchPower = 0.25f;
    }

    public static void winchDown() {
        data.winchPower = -0.25f;
    }

    public static void winchStop() {
        data.winchPower = 0f;
    }

    public static void wristOut() {
        data.wristPower = 0.25f;
    }

    public static void wristIn() {
        data.wristPower = -0.25f;
    }

    public static void wristStop() { data.wristPower = 0; }

    public static void openClaw() {
        data.leftClawPosition = 0.75f;
        data.rightClawPosition = 0.25f;
    }

    public static void closeClaw() {
        data.leftClawPosition = 0.5f;
        data.rightClawPosition = 0.5f;
    }

    public static void tick() {
        float liftPower = liftPower();
        armMotor.setPower(liftPower);
//        winchMotor.setPower(data.winchPower);
        wristMotor.setPower(data.wristPower);
        leftClaw.setPosition(data.leftClawPosition);
        rightClaw.setPosition(data.rightClawPosition);
        telemetry.addData("targetArmPosition", data.targetLiftPosition);
        telemetry.addData("currentArmPosition", armMotor.getCurrentPosition());
        telemetry.addData("armPower", liftPower);
        telemetry.addData("winchPower", data.winchPower);
        telemetry.addData("leftClawPosition", data.leftClawPosition);
        telemetry.addData("rightClawPosition", data.rightClawPosition);
    }

    private static float clip(float clipNumber) {
        if (clipNumber > 1) {return 1;}
        if (clipNumber < -1) {return -1;}
        return clipNumber;
    }
    private static float liftPower() {
        float error = data.targetLiftPosition - armMotor.getCurrentPosition();
        float roughPower = 1 / inverseArmGain * error;
        return clip(roughPower);
    }
}
