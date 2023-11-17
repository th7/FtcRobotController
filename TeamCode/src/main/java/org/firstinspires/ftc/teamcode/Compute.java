package org.firstinspires.ftc.teamcode;

import com.sun.tools.javac.util.Pair;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Compute {
  public final int armUpPosition = 140;
  public final int armDownPosition = 0;
  public final int armSlowThreshold = 25;

  public final float armFast = 0.1f;
  public final float armSlow = 0.05f;

  public Memory memory = new Memory();
  public Output compute(Input input) {
    Output output = new Output();

    output.armMotorPower = arm(input.dPadUp, input.dPadDown, input.cross, input.triangle, input.armPosition);
    output.movement = manualDrive(input.gameStickRightX, input.gameStickLeftY, input.gameStickLeftX);
    output.winchMotorPower = winch(input.dPadRight, input.dPadLeft);

    manualClaw(input.leftBumper, input.rightBumper, input.leftTrigger, input.rightTrigger);
    output.topClawPosition = memory.topClawPosition;
    output.bottomClawPosition = memory.bottomClawPosition;

    output.telemetry.add(new Output.Telemetry("gameStickLeftX", input.gameStickLeftX));
    output.telemetry.add(new Output.Telemetry("gameStickLeftY", input.gameStickLeftY));
    output.telemetry.add(new Output.Telemetry("gameStickRightX", input.gameStickRightX));
    output.telemetry.add(new Output.Telemetry("gameStickRightY", input.gameStickRightY));

    output.telemetry.add(new Output.Telemetry("frontLeftPower", output.movement.frontLeftPower));
    output.telemetry.add(new Output.Telemetry("frontRightPower", output.movement.frontRightPower));
    output.telemetry.add(new Output.Telemetry("rearLeftPower", output.movement.rearLeftPower));
    output.telemetry.add(new Output.Telemetry("rearRightPower", output.movement.rearRightPower));

    return output;
  }

  public Output computeAutonomous(Input input) {
    Output output = new Output();

    if (!inProgress(input.wheelPosition, input.yaw)) {
      runStep(memory.currentStep + 1);
      output.telemetry.add(new Output.Telemetry("inProgress", false));
    } else {
      output.telemetry.add(new Output.Telemetry("inProgress", true));
    }

    output.telemetry.add(new Output.Telemetry("currentStep", memory.currentStep));


    output.topClawPosition = memory.topClawPosition;
    output.bottomClawPosition = memory.bottomClawPosition;
    output.armMotorPower = autoArmPower(input.armPosition);

//    if (inProgressTurn(input.yaw)) {
//      output.movement = autoTurn(input.yaw, memory.targetAngle);
//    } else {
//      output.movement = autoMove(input.wheelPosition, memory.targetMovePosition);
//    }

    Output.Movement turnMovement = autoTurn(input.yaw, memory.targetAngle);
    Output.Movement moveMovement = autoMove(input.wheelPosition, memory.targetMovePosition);

    output.movement.frontLeftPower = clip(turnMovement.frontLeftPower + moveMovement.frontLeftPower);
    output.movement.frontRightPower = clip(turnMovement.frontRightPower + moveMovement.frontRightPower);
    output.movement.rearLeftPower = clip(turnMovement.rearLeftPower + moveMovement.rearLeftPower);
    output.movement.rearRightPower = clip(turnMovement.rearRightPower + moveMovement.rearRightPower);

    output.telemetry.add(new Output.Telemetry("targetMovePosition", memory.targetMovePosition));
    output.telemetry.add(new Output.Telemetry("wheelPosition", input.wheelPosition));

    output.telemetry.add(new Output.Telemetry("targetAngle", memory.targetAngle));
    output.telemetry.add(new Output.Telemetry("yaw", input.yaw));

    output.telemetry.add(new Output.Telemetry("frontLeftPower", output.movement.frontLeftPower));
    output.telemetry.add(new Output.Telemetry("frontRightPower", output.movement.frontRightPower));
    output.telemetry.add(new Output.Telemetry("rearLeftPower", output.movement.rearLeftPower));
    output.telemetry.add(new Output.Telemetry("rearRightPower", output.movement.rearRightPower));

    return output;
  }

  private Output.Movement manualDrive(float gameStickRightX, float gameStickLeftY, float gameStickLeftX) {
    Output.Movement movement = new Output.Movement();
    Output.Movement turnMovement = turnOutput(gameStickRightX);
    Output.Movement moveMovement = manualMove(gameStickLeftY);
    Output.Movement strafeMovement = strafeOutput(gameStickLeftX);

    movement.frontLeftPower = clip(turnMovement.frontLeftPower + moveMovement.frontLeftPower + strafeMovement.frontLeftPower);
    movement.frontRightPower = clip(turnMovement.frontRightPower + moveMovement.frontRightPower + strafeMovement.frontRightPower);
    movement.rearLeftPower = clip(turnMovement.rearLeftPower + moveMovement.rearLeftPower + strafeMovement.rearLeftPower);
    movement.rearRightPower = clip(turnMovement.rearRightPower + moveMovement.rearRightPower + strafeMovement.rearRightPower);

    return movement;
  }

  private void manualClaw(boolean leftBumper, boolean rightBumper, float leftTrigger, float rightTrigger) {
    if (rightBumper) {
      memory.topClawPosition = 1d;
    }
    if (leftBumper) {
      memory.topClawPosition = 0d;
    }
    if (rightTrigger != 0) {
      memory.bottomClawPosition = 1d;
    }
    if (leftTrigger != 0) {
      memory.bottomClawPosition = 0d;
    }
  }

  private Output.Movement autoMove(int wheelPosition, int targetMovePosition) {
    Output.Movement movement = new Output.Movement();

    int distanceToMove = targetMovePosition - wheelPosition;
    float power = autoMinimum(clip(distanceToMove / 100f));

    movement.frontLeftPower = power;
    movement.frontRightPower = power;
    movement.rearLeftPower = power;
    movement.rearRightPower = power;

    return movement;
  }

  private Output.Movement autoTurn(double yaw, double targetAngle) {
    Output.Movement movement = new Output.Movement();

    double distanceToTurn = targetAngle - yaw;
    float power = autoMinimum(clip((float) (distanceToTurn / 180d)));

    if (Math.abs(distanceToTurn) > 180) {
      movement.frontLeftPower = power;
      movement.frontRightPower = -power;
      movement.rearLeftPower = power;
      movement.rearRightPower = -power;
    } else {
      movement.frontLeftPower = -power;
      movement.frontRightPower = power;
      movement.rearLeftPower = -power;
      movement.rearRightPower = power;
    }
    return movement;
  }

  private Output.Movement turnOutput(float gameStickRightX) {
    Output.Movement movement = new Output.Movement();
    movement.frontLeftPower = gameStickRightX;
    movement.frontRightPower = -gameStickRightX;
    movement.rearLeftPower = gameStickRightX;
    movement.rearRightPower = -gameStickRightX;
    return movement;
  }

  private Output.Movement manualMove(float gameStickLeftY) {
    Output.Movement movement = new Output.Movement();

    movement.frontLeftPower = -gameStickLeftY;
    movement.frontRightPower = -gameStickLeftY;
    movement.rearLeftPower = -gameStickLeftY;
    movement.rearRightPower = -gameStickLeftY;

    return movement;
  }

  private Output.Movement strafeOutput(float gameStickLeftX) {
    Output.Movement movement = new Output.Movement();
    movement.frontLeftPower = -gameStickLeftX;
    movement.frontRightPower = gameStickLeftX;
    movement.rearLeftPower = gameStickLeftX;
    movement.rearRightPower = -gameStickLeftX;

    return movement;
  }

  private float arm(boolean dPadUp, boolean dPadDown, boolean cross, boolean triangle, int armPosition) {
    if (dPadUp) {
      memory.autoMoveArm = false;
      return 0.25f;
    }

    if (dPadDown) {
      memory.autoMoveArm = false;
      return -0.25f;
    }

    if (triangle) {
      memory.autoMoveArm = true;
      memory.targetArmPosition = armUpPosition;
    }

    if (cross) {
      memory.autoMoveArm = true;
      memory.targetArmPosition = armDownPosition;
    }

    if (!memory.autoMoveArm) {
      return 0f;
    }

    return autoArmPower(armPosition);
  }

  public float autoArmPower(int armPosition) {
    if (armPosition < memory.targetArmPosition && memory.targetArmPosition - armPosition <= armSlowThreshold) {
      return armSlow;
    }

    if (armPosition > memory.targetArmPosition && armPosition - memory.targetArmPosition <= armSlowThreshold) {
      return -armSlow;
    }

    if (armPosition < memory.targetArmPosition) {
      return armFast;
    }

    if (armPosition > memory.targetArmPosition) {
      return -armFast;
    }

    return 0f;
  }

  private float winch(boolean dPadRight, boolean dPadLeft) {
    if (dPadRight) {
      return 1f;
    }

    if (dPadLeft) {
      return -1f;
    }

    return 0f;
  }

  private float clip(float unclipped) {
    if (unclipped < -1) {
      return -1f;
    }

    if (unclipped > 1) {
      return 1f;
    }

    return unclipped;
  }

  private float autoMinimum(float power) {
    if (-0.05 < power && power < 0) {
      return -0.05f;
    }

    if (0 < power && power < 0.05) {
      return 0.05f;
    }

    return power;
  }

  public void runStep(int stepNumber) {
    switch(stepNumber) {
      case 1: step1(); break;
      case 2: step2(); break;
      case 3: step3(); break;
      case 4: step4(); break;
      case 5: step5(); break;
      case 6: step6(); break;
      case 7: step7(); break;
      case 8: step8(); break;
      case 9: step9(); break;
    }
    memory.currentStep = stepNumber;
  }

  public boolean inProgress(int wheelPosition, double yaw) {
    return inProgressMove(wheelPosition) || inProgressTurn(yaw);
  }

  public boolean inProgressMove(int wheelPosition) {
    return memory.targetMovePosition != wheelPosition;
  }

  public boolean inProgressTurn(double yaw) {
    return !closeEnough(memory.targetAngle, yaw);
  }


  public boolean closeEnough(double a, double b) {
    double difference = Math.abs(a - b);
    return difference <= 1;
  }

  public void step1() {
    move(1000);
  }

  public void step2() {
    turn(90);
  }

  public void step3() {
    move(1000);
  }

  public void step4() {
    turn(90);
  }

  public void step5() {
    move(1000);
  }

  public void step6() {
    turn(90);
  }

  public void step7() {
    move(1000);
  }

  public void step8() {
    turn(90);
  }

  public void step9() {
    memory.targetArmPosition = armUpPosition;
  }
  public void turn(double turnAmount) {
    double targetAngle = memory.targetAngle + turnAmount;

    if (targetAngle > 180) {
      memory.targetAngle = targetAngle - 360;
    } else if (targetAngle < -180) {
      memory.targetAngle = targetAngle + 360;
    } else {
      memory.targetAngle = targetAngle;
    }
  }

  public void move(int moveAmount) {
    memory.targetMovePosition += moveAmount;
  }

  // private float logistic(float offset) {
  //   float e = 2.71828f; // should be constant
  //   float k = -0.1f; // adjust to tune steepness of curve
  //   return 2 / (1 + Math.pow(e, k * offset);
  // }
}
