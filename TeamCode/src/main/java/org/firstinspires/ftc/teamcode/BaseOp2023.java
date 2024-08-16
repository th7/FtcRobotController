package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public abstract class BaseOp2023 extends OpMode {
  final ElapsedTime runtime = new ElapsedTime();
  DcMotor leftFront = null;
  DcMotor rightFront = null;
  DcMotor leftBack = null;
  DcMotor rightBack = null;

  DcMotor armMotor1 = null;
  DcMotor armMotor2 = null;
  DcMotor winchMotor = null;
  IMU imu = null;
  Servo topClaw = null;
  Servo bottomClaw = null;
  Servo droneLauncher = null;
  VisionPortal visionPortal = null;
  AprilTagProcessor aprilTagProcessor = null;

  @Override
  public void init() {
    leftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
    rightFront = hardwareMap.get(DcMotor.class, "RightFront");
    leftBack = hardwareMap.get(DcMotor.class, "LeftBack");
    rightBack = hardwareMap.get(DcMotor.class, "RightBack");

    armMotor1 = hardwareMap.get(DcMotor.class, "ArmMotor1");
    armMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor1.setDirection(DcMotor.Direction.REVERSE);
    armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    armMotor2 = hardwareMap.get(DcMotor.class, "ArmMotor2");
    armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor2.setDirection(DcMotor.Direction.REVERSE);
    armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    winchMotor = hardwareMap.get(DcMotor.class, "WinchMotor");

    imu = hardwareMap.get(IMU.class, "IMU");
    imu.resetYaw();

    topClaw = hardwareMap.get(Servo.class, "TopClaw");
    bottomClaw = hardwareMap.get(Servo.class, "BottomClaw");

    droneLauncher = hardwareMap.get(Servo.class, "DroneLaunch");

    droneLauncher.setDirection(Servo.Direction.REVERSE);
    droneLauncher.setPosition(ManualDroneController.initialPosition);

    aprilTagProcessor = new AprilTagProcessor.Builder().build();

    visionPortal = new VisionPortal.Builder()
            .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
            .addProcessor(aprilTagProcessor)
            .build();
  }

  @Override
  public void init_loop() {}

  @Override
  public void start() {
    runtime.reset();
  }

  @Override
  public void stop() {}
}