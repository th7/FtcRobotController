package org.firstinspires.ftc.teamcode;

public class Compute {
  public final int armUpPosition = 50;
  public final int armDownPosition = 0;
  public final int armSlowThreshold = 25;

  public final float armFast = 0.1f;
  public final float armSlow = 0.05f;

  public final double clawOpen = 0.25d;
  public final double clawClosed = 0d;

  public final double oneTile = 1195d;

  public final double turnRight = -90d;
  public final double turnLeft = -turnRight;

  public final Memory memory;
  public Input input;

  Compute(Memory memory) {
    this.memory = memory;
  }

  public void closeClaws() {
    memory.topClawPosition = clawClosed;
    memory.bottomClawPosition = clawClosed;
  }

  public void openBottomClaw() {
    memory.bottomClawPosition = clawOpen;
  }

  public void openTopClaw() {
    memory.topClawPosition = clawOpen;
  }

  public Output.Movement driveMovement() {
    Output.Movement turnMovement = autoTurn(5d);
    Output.Movement moveMovement = autoMove();

    return turnMovement.add(0f, 1f, moveMovement);
  }

  public Output.Movement manualDrive() {
    return manualTurn().add(0f, 1f, manualMove(), manualStrafe());
  }

  public void manualClaw() {
    if (input.rightBumper) {
      memory.topClawPosition = clawClosed;
    }
    if (input.leftBumper) {
      memory.topClawPosition = clawOpen;
    }
    if (input.rightTrigger > 0) {
      memory.bottomClawPosition = clawClosed;
    }
    if (input.leftTrigger > 0) {
      memory.bottomClawPosition = clawOpen;
    }
  }

  private Output.Movement autoMove() {
    int distanceToMove = memory.targetMovePosition - input.wheelPosition;
    float power = (float) distanceToMove / 200f;

    return Output.Movement.move(power, 0.05f, 0.6f);
  }

  public Output.Movement autoTurn(double gain) {
    double distanceToTurn = memory.targetAngle - input.yaw;
    double shortDistanceToTurn;

    if (distanceToTurn > 180) {
      shortDistanceToTurn = distanceToTurn - 360;
    } else if (distanceToTurn < -180) {
      shortDistanceToTurn = distanceToTurn + 360;
    } else {
      shortDistanceToTurn = distanceToTurn;
    }

    float power = (float) (shortDistanceToTurn * gain / 90d);

    return Output.Movement.turn(power, 0.05f, 0.6f);
  }

  private Output.Movement manualTurn() {
    return Output.Movement.turn(input.gameStickRightX, 0f, 1f);
  }

  private Output.Movement manualMove() {
    return Output.Movement.move(-input.gameStickLeftY, 0f, 1f);
  }

  private Output.Movement manualStrafe() {
    return Output.Movement.strafe(input.gameStickLeftX, 0f, 1f);
  }

  public float arm() {
    if (input.dPadUp) {
      memory.autoMoveArm = false;
      return 0.25f;
    }

    if (input.dPadDown) {
      memory.autoMoveArm = false;
      return -0.25f;
    }

    if (!memory.autoMoveArm) {
      return 0f;
    }

    return autoArmPower();
  }

  public float autoArmPower() {
    if (input.armPosition < memory.targetArmPosition && memory.targetArmPosition - input.armPosition <= armSlowThreshold) {
      return armSlow;
    }

    if (input.armPosition > memory.targetArmPosition && input.armPosition - memory.targetArmPosition <= armSlowThreshold) {
      return -armSlow;
    }

    if (input.armPosition < memory.targetArmPosition) {
      return armFast;
    }

    if (input.armPosition > memory.targetArmPosition) {
      return -armFast;
    }

    return 0f;
  }

  public float winch() {
    if (input.triangle) {
      return 1f;
    }

    if (input.cross) {
      return -1f;
    }

    return 0f;
  }

  private boolean closeEnough(double a, double b, int threshold) {
    double difference = Math.abs(a - b);
    return difference <= threshold;
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
    memory.targetMovePosition = input.wheelPosition + moveAmount;
  }

  public void move(double moveAmount) {
    move((int) moveAmount);
  }

  public void waitFor(double waitSeconds) {
    memory.targetWaitSeconds = input.elapsedSeconds + waitSeconds;
  }

  public boolean waitComplete() {
    return input.elapsedSeconds > memory.targetWaitSeconds;
  }

  public boolean moveCloseEnough() {
    return closeEnough(input.wheelPosition, memory.targetMovePosition, 8);
  }

  public boolean turnCloseEnough() {
    return closeEnough(input.yaw, memory.targetAngle, 1);
  }
}
