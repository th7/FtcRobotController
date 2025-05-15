package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private static DcMotor armMotor;
    private static DcMotor winchMotor;
    private static DcMotor wristMotor;
    private static Servo leftClaw;
    private static Servo rightClaw;
    private static TouchSensor armLimitSwitch;
    private static Telemetry telemetry;
    private static ElapsedTime runtime;
    private static final float inverseArmGain = 100;
    private static final int minLiftPosition = 0;
    private static final int maxLiftPosition = 2700;
    private static final int manualArmRate = 50;
    private static final double clawWaitSeconds = 0.6d;
    private static Arm data;
    private int targetArmPosition = 0;
    private float winchPower = 0f;
    private float wristPower = 0f;
    private float leftClawPosition = 0.5f;
    private float rightClawPosition = 0.5f;
    private double clawMoveStartedSeconds = -1d;


    public static void init() {
        armMotor = Hardware.armMotor;
        rightClaw = Hardware.rightClaw;
        leftClaw = Hardware.leftClaw;
//        winchMotor = Hardware.winchMotor;
        wristMotor = Hardware.wristMotor;
        armLimitSwitch = Hardware.armLimitSwitch;
        runtime = Hardware.runtime;
        telemetry = Hardware.telemetry;
        data = new Arm();
        telemetry.addData("Arm.init()", true);
    }

    private static boolean clawDone() {
        return runtime.seconds() > data.clawMoveStartedSeconds + clawWaitSeconds;
    }

    private static boolean armMoveDone() {
        return Util.closeEnough(armMotor.getCurrentPosition(), data.targetArmPosition, 5);
    }

    public static boolean done() {
        return clawDone() && armMoveDone();
    }



    public static void manualUp() {
        data.targetArmPosition = armMotor.getCurrentPosition() + manualArmRate;
    }

    public static void manualDown() {
        data.targetArmPosition = armMotor.getCurrentPosition() - manualArmRate;
    }

    public static void armToFloor() { data.targetArmPosition = maxLiftPosition; }

    public static void armToFloorForwards() {
        data.targetArmPosition = minLiftPosition;
    }

    public static void armToLowBasket() { data.targetArmPosition = 1500; }

    public static void armToFloorBackward () { data.targetArmPosition = 2600; }

    public static void armToAboveFloor () {
        data.targetArmPosition = 100;
        telemetry.addData("armToAboveFloorIsCalled", true);

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

    public static void wristOut() {
        data.wristPower = 0.25f;
    }

    public static void wristIn() {
        data.wristPower = -0.25f;
    }

    public static void wristStop() { data.wristPower = 0; }

    public static void openClaw() {
        data.clawMoveStartedSeconds = runtime.seconds();
        data.leftClawPosition = 0.75f;
        data.rightClawPosition = 0.25f;
    }

    public static void closeClaw() {
        data.clawMoveStartedSeconds = runtime.seconds();
        data.leftClawPosition = 0.5f;
        data.rightClawPosition = 0.5f;
    }

    public static void tick() {
        float armPower = armPower();
        armMotor.setPower(armPower);
//        winchMotor.setPower(data.winchPower);
        wristMotor.setPower(data.wristPower);
        leftClaw.setPosition(data.leftClawPosition);
        rightClaw.setPosition(data.rightClawPosition);
        if (armLimitSwitch.isPressed()) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if (data.targetArmPosition < 0) { data.targetArmPosition = 0; }
        }
        telemetry.addData("targetArmPosition", data.targetArmPosition);
        telemetry.addData("currentArmPosition", armMotor.getCurrentPosition());
        telemetry.addData("armPower", armPower);
        telemetry.addData("winchPower", data.winchPower);
        telemetry.addData("leftClawPosition", data.leftClawPosition);
        telemetry.addData("rightClawPosition", data.rightClawPosition);
        telemetry.addData("armLimitSwitchPressed", armLimitSwitch.isPressed());
    }

    private static float clip(float clipNumber) {
        if (clipNumber > 1) {return 1;}
        if (clipNumber < -1) {return -1;}
        return clipNumber;
    }
    private static float armPower() {
        float error = data.targetArmPosition - armMotor.getCurrentPosition();
        float roughPower = 1 / inverseArmGain * error;
        if (roughPower < 0 && armLimitSwitch.isPressed()) {
            return 0;
        }
        return clip(roughPower);
    }
}
