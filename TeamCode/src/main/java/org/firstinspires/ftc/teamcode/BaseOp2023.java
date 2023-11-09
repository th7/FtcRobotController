package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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

  @Override
  public void runOpMode() {
    leftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
    rightFront = hardwareMap.get(DcMotor.class, "RightFront");
    leftBack = hardwareMap.get(DcMotor.class, "LeftBack");
    rightBack = hardwareMap.get(DcMotor.class, "RightBack");
    armMotor1 = hardwareMap.get(DcMotor.class, "ArmMotor1");
    armMotor2 = hardwareMap.get(DcMotor.class, "ArmMotor2");
    winchMotor = hardwareMap.get(DcMotor.class, "WinchMotor");

    armMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    winchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    armMotor1.setDirection(DcMotor.Direction.REVERSE);
    armMotor2.setDirection(DcMotor.Direction.REVERSE);

    typeSpecificInit();

    telemetry.addData("Status", "Initialized");
    telemetry.update();
    waitForStart();
    runtime.reset();

    while (opModeIsActive()) {
      Input input = new Input();

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
      input.rightTrigger = gamepad1.right_trigger;

      input.armPosition = armMotor1.getCurrentPosition();
      input.wheelPosition = leftFront.getCurrentPosition();

      Output output = compute(input);

      leftFront.setPower(output.frontLeftPower);
      rightFront.setPower(output.frontRightPower);
      leftBack.setPower(output.rearLeftPower);
      rightBack.setPower(output.rearRightPower);

      armMotor1.setPower(output.armMotorPower);
      armMotor2.setPower(output.armMotorPower);
      winchMotor.setPower(output.winchMotorPower);
    }
  }

  protected abstract Output compute(Input input);

  protected abstract void typeSpecificInit();
}
