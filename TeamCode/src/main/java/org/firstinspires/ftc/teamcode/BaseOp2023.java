package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Disabled
@TeleOp(name="BaseOp2023", group="Linear Opmode")
public abstract class BaseOp2023 extends LinearOpMode {
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
  public void runOpMode() {
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
//    winchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    imu = hardwareMap.get(IMU.class, "IMU");
    imu.resetYaw();

    topClaw = hardwareMap.get(Servo.class, "TopClaw");
    bottomClaw = hardwareMap.get(Servo.class, "BottomClaw");

    droneLauncher = hardwareMap.get(Servo.class, "DroneLaunch");

    droneLauncher.setDirection(Servo.Direction.REVERSE);
    droneLauncher.setPosition(new Output().launcherPosition);

    typeSpecificInit();

    Memory memory = new Memory();

    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    runtime.reset();

     // Check to see if the robot is enabled
    while (opModeIsActive()) {
      Input input = new Input();

      input.elapsedSeconds = runtime.seconds();

      input.gameStickLeftX = gamepad1.left_stick_x;
      input.gameStickLeftY = gamepad1.left_stick_y;
      input.gameStickRightX = gamepad1.right_stick_x;
      input.gameStickRightY = gamepad1.right_stick_y;

      input.dPadUp = gamepad1.dpad_up;
      input.dPadDown = gamepad1.dpad_down;
      input.dPadLeft = gamepad1.dpad_left;
      input.dPadRight = gamepad1.dpad_right;
      input.triangle = gamepad1.triangle;
      input.cross = gamepad1.cross;
      input.circle = gamepad1.circle;
      input.rightTrigger = gamepad1.right_trigger;
      input.leftTrigger = gamepad1.left_trigger;
      input.rightBumper = gamepad1.right_bumper;
      input.leftBumper = gamepad1.left_bumper;

      input.armPosition = armMotor1.getCurrentPosition();
      input.wheelPosition = leftFront.getCurrentPosition();

      input.yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

      Compute compute = new Compute(memory, input);
      Output output = compute(compute);

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

  protected abstract Output compute(Compute compute);

  protected abstract void typeSpecificInit();
}