package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class Hardware {
    public static HardwareMap hardwareMap;

    public static Telemetry telemetry;
    public static ElapsedTime runtime = new ElapsedTime();
    public static DcMotor leftFront = null;
    public static DcMotor rightFront = null;
    public static DcMotor leftBack = null;
    public static DcMotor rightBack = null;
    public static DcMotor armMotor = null;
    public static DcMotor wristMotor = null;
    public static DcMotor winchMotor = null;
    public static IMU imu = null;
    public static Servo rightClaw = null;
    public static Servo leftClaw = null;
    public static Servo droneLauncher = null;
    public static TouchSensor armLimitSwitch = null;
    public static VisionPortal visionPortal = null;
    public static AprilTagProcessor aprilTagProcessor = null;





    public static void init() {
        leftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
        rightFront = hardwareMap.get(DcMotor.class, "RightFront");
        leftBack = hardwareMap.get(DcMotor.class, "LeftBack");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack = hardwareMap.get(DcMotor.class, "RightBack");
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    liftMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wristMotor = hardwareMap.get(DcMotor.class, "WristMotor");
        wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    armMotor2 = hardwareMap.get(DcMotor.class, "ArmMotor2");
//    armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    armMotor2.setDirection(DcMotor.Direction.REVERSE);
//    armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        winchMotor = hardwareMap.get(DcMotor.class, "WinchMotor");
//        winchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        winchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, "IMU");
        imu.resetYaw();

    rightClaw = hardwareMap.get(Servo.class, "RightClaw");
    leftClaw = hardwareMap.get(Servo.class, "LeftClaw");
//
//    droneLauncher = hardwareMap.get(Servo.class, "DroneLaunch");
//
//    droneLauncher.setDirection(Servo.Direction.REVERSE);
//    droneLauncher.setPosition(ManualDroneController.initialPosition);
//
    armLimitSwitch = hardwareMap.get(TouchSensor.class, "ArmLimitSwitch");

    aprilTagProcessor = new AprilTagProcessor.Builder().build();

    visionPortal = new VisionPortal.Builder()
            .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
            .addProcessor(aprilTagProcessor)
            .build();

    telemetry.addData("Hardware.init()", true);
    }

    public static void initAuto() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Hardware.initAuto()", true);
    }
}
