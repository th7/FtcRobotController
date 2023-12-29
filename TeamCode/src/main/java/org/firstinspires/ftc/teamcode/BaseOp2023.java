package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class BaseOp2023 extends OpMode {
  Memory memory = new Memory();
  Compute compute = new Compute(memory);
  private final ElapsedTime runtime = new ElapsedTime();
  DcMotor leftFront = null;
  DcMotor rightFront = null;
  DcMotor leftBack = null;
  DcMotor rightBack = null;

  private DcMotor armMotor1 = null;
  private DcMotor armMotor2 = null;
  private DcMotor winchMotor = null;
  private IMU imu = null;
  private Servo topClaw = null;
  private Servo bottomClaw = null;
  private Servo droneLauncher = null;

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
    droneLauncher.setPosition(new Output().launcherPosition);
  }

  @Override
  public void init_loop() {}

  @Override
  public void start() {
    runtime.reset();
  }

  @Override
  public void loop() {
    compute.input = collectInputs();
    Output output = compute();
    setOutputs(output);
  }

  @Override
  public void stop() {}

  public Input collectInputs() {
    Input input = new Input();

    input.elapsedSeconds = runtime.seconds();

    input.armPosition = armMotor1.getCurrentPosition();
    input.wheelPosition = leftFront.getCurrentPosition();

    input.yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

    return input;
  }

  public Output compute() { return new Output(); }

  private void setOutputs(Output output) {
    output.telemetry.forEach((tele) -> telemetry.addData(tele.name, tele.value));
    telemetry.update();

    leftFront.setPower(output.movement.frontLeftPower);
    rightFront.setPower(output.movement.frontRightPower);
    leftBack.setPower(output.movement.rearLeftPower);
    rightBack.setPower(output.movement.rearRightPower);

    armMotor1.setPower(output.armMotorPower);
    armMotor2.setPower(output.armMotorPower);
    winchMotor.setPower(output.winchMotorPower);

    topClaw.setPosition(output.topClawPosition);
    bottomClaw.setPosition(output.bottomClawPosition);

    droneLauncher.setPosition(output.launcherPosition);
  }
}