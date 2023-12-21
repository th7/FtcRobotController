package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.state.utils.Stateful;
import org.firstinspires.ftc.teamcode.state.utils.State;
import org.firstinspires.ftc.teamcode.state.utils.LinearStateMachine;

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
  public Stateful stateMachine;
  public Input input;

  Compute(Memory memory) {
    this.memory = memory;
  }

  Output computeAutonomous() {
    Output output = new Output();

    if (stateMachine.done()) {
      output.addTel("done", true);
      return output;
    }

    output.topClawPosition = memory.topClawPosition;
    output.bottomClawPosition = memory.bottomClawPosition;
    output.armMotorPower = autoArmPower(input.armPosition);
    output.movement = stateMachine.movement();

    output.addTel("targetMovePosition", memory.targetMovePosition);
    output.addTel("wheelPosition", input.wheelPosition);

    output.addTel("targetAngle", memory.targetAngle);
    output.addTel("yaw", input.yaw);

    output.addTel("frontLeftPower", output.movement.frontLeftPower);
    output.addTel("frontRightPower", output.movement.frontRightPower);
    output.addTel("rearLeftPower", output.movement.rearLeftPower);
    output.addTel("rearRightPower", output.movement.rearRightPower);

    return output;
  }

  public Output teleOp() {
    Output output = new Output();

    output.armMotorPower = arm(input.dPadUp, input.dPadDown, input.armPosition);
    output.movement = manualDrive(input.gameStickRightX, input.gameStickLeftY, input.gameStickLeftX);
    output.winchMotorPower = winch(input.triangle, input.cross);

    manualClaw(input.leftBumper, input.rightBumper, input.leftTrigger, input.rightTrigger);
    output.topClawPosition = memory.topClawPosition;
    output.bottomClawPosition = memory.bottomClawPosition;

    if (input.circle) {
      output.launcherPosition = 0.7d;
    }

    output.addTel("gameStickLeftX", input.gameStickLeftX);
    output.addTel("gameStickLeftY", input.gameStickLeftY);
    output.addTel("gameStickRightX", input.gameStickRightX);
    output.addTel("gameStickRightY", input.gameStickRightY);

    output.addTel("frontLeftPower", output.movement.frontLeftPower);
    output.addTel("frontRightPower", output.movement.frontRightPower);
    output.addTel("rearLeftPower", output.movement.rearLeftPower);
    output.addTel("rearRightPower", output.movement.rearRightPower);

    output.addTel("circle", input.circle);
    output.addTel("launcherPosition", output.launcherPosition);

    output.addTel("leftTrigger", input.leftTrigger);
    output.addTel("rightTrigger", input.rightTrigger);
    output.addTel("leftBumper", input.leftBumper);
    output.addTel("rightBumper", input.rightBumper);
    output.addTel("topClawPosition", output.topClawPosition);
    output.addTel("bottomClawPosition", output.bottomClawPosition);

    return output;
  }

  public LinearStateMachine teamProp() {
    return new LinearStateMachine(
            closeClawsState(),
            moveToSpikeMarks(),
            openBottomClawState(),
            moveBackFromSpikeMarks()
    );
  }

  public LinearStateMachine backstage(double turn) {
    return new LinearStateMachine(
            teamProp(),

            turnState(turn),
            toBackStageShort(),
            openTopClawState(),
            nudgeBack()
    );
  }

  public LinearStateMachine frontStage(int turnDirection) {
    return new LinearStateMachine(
            teamProp(),

            turnState(-90 * turnDirection),
            moveToWing(),
            turnState(90 * turnDirection),
            wingToMiddle(),
            turnState(45 * turnDirection),
            moveToMiddle(),
            turnState(45 * turnDirection),
            moveBackStageLong(),
            openTopClawState(),
            nudgeBack()
    );
  }

  public LinearStateMachine backStageRed() {
    return backstage(turnRight);
  }

  public LinearStateMachine backStageBlue() {
    return backstage(turnLeft);
  }

  public LinearStateMachine frontStageRed() {
    return frontStage(1);
  }

  public LinearStateMachine frontStageBlue() {
    return frontStage(-1);
  }

  public State closeClawsState() {
    return new State(
            () -> { closeClaws(); waitFor(0.6); },
            () -> input.elapsedSeconds > memory.targetWaitSeconds
    );
  }

  private State moveToSpikeMarks() {
    return moveState(oneTile * 1.3);
  }

  private State openBottomClawState() {
    return new State(
            () -> { openBottomClaw(); waitFor(0.6); },
            () -> input.elapsedSeconds > memory.targetWaitSeconds
    );
  }

  private State moveBackFromSpikeMarks() {
    return moveState(-oneTile * 0.9);
  }

  private Stateful toBackStageShort() {
    return moveState(oneTile * 1.8);
  }

  private Stateful moveToWing() {
    return moveState(oneTile * 0.8);
  }

  private Stateful wingToMiddle() {
    return moveState(oneTile * 1.7);
  }

  private Stateful moveToMiddle() {
    return moveState(oneTile * 0.8);
  }

  private Stateful moveBackStageLong() {
    return moveState(oneTile * 4);
  }

  private Stateful openTopClawState() {
    return new State(
            () -> { openTopClaw(); waitFor(0.6); },
            () -> input.elapsedSeconds > memory.targetWaitSeconds
    );
  }

  private Stateful nudgeBack() {
    return moveState(-oneTile * 0.2);
  }

  public State moveState(double distance) {
    return new State(
            () -> move(distance),
            this::driveMovement,
            () -> closeEnough(input.wheelPosition, memory.targetMovePosition, 8)
    );
  }

  public State turnState(double angle) {
    return new State(
            () -> turn(angle),
            this::turnMovement,
            () -> closeEnough(input.yaw, memory.targetAngle, 1)
    );
  }

  private void closeClaws() {
    memory.topClawPosition = clawClosed;
    memory.bottomClawPosition = clawClosed;
  }

  private void openBottomClaw() {
    memory.bottomClawPosition = clawOpen;
  }

  private void openTopClaw() {
    memory.topClawPosition = clawOpen;
  }

  private Output.Movement turnMovement() {
    return autoTurn(input.yaw, memory.targetAngle, 1d);
  }

  private Output.Movement driveMovement() {
    Output.Movement movement = new Output.Movement();
    Output.Movement turnMovement = autoTurn(input.yaw, memory.targetAngle, 5d);
    Output.Movement moveMovement = autoMove(input.wheelPosition, memory.targetMovePosition);

    movement.frontLeftPower = clip(turnMovement.frontLeftPower + moveMovement.frontLeftPower);
    movement.frontRightPower = clip(turnMovement.frontRightPower + moveMovement.frontRightPower);
    movement.rearLeftPower = clip(turnMovement.rearLeftPower + moveMovement.rearLeftPower);
    movement.rearRightPower = clip(turnMovement.rearRightPower + moveMovement.rearRightPower);

    return movement;
  }

  private Output.Movement manualDrive(float gameStickRightX, float gameStickLeftY, float gameStickLeftX) {
    Output.Movement movement = new Output.Movement();
    Output.Movement turnMovement = manualTurn(gameStickRightX);
    Output.Movement moveMovement = manualMove(gameStickLeftY);
    Output.Movement strafeMovement = manualStrafe(gameStickLeftX);

    movement.frontLeftPower = clip(turnMovement.frontLeftPower + moveMovement.frontLeftPower + strafeMovement.frontLeftPower);
    movement.frontRightPower = clip(turnMovement.frontRightPower + moveMovement.frontRightPower + strafeMovement.frontRightPower);
    movement.rearLeftPower = clip(turnMovement.rearLeftPower + moveMovement.rearLeftPower + strafeMovement.rearLeftPower);
    movement.rearRightPower = clip(turnMovement.rearRightPower + moveMovement.rearRightPower + strafeMovement.rearRightPower);

    return movement;
  }

  private void manualClaw(boolean leftBumper, boolean rightBumper, float leftTrigger, float rightTrigger) {
    if (rightBumper) {
      memory.topClawPosition = clawClosed;
    }
    if (leftBumper) {
      memory.topClawPosition = clawOpen;
    }
    if (rightTrigger > 0) {
      memory.bottomClawPosition = clawClosed;
    }
    if (leftTrigger > 0) {
      memory.bottomClawPosition = clawOpen;
    }
  }

  private Output.Movement autoMove(int wheelPosition, int targetMovePosition) {
    Output.Movement movement = new Output.Movement();

    int distanceToMove = targetMovePosition - wheelPosition;
    float power = autoMinimum(autoClip((float) distanceToMove / 200f));

    movement.frontLeftPower = power;
    movement.frontRightPower = power;
    movement.rearLeftPower = power;
    movement.rearRightPower = power;

    return movement;
  }

  private Output.Movement autoTurn(double yaw, double targetAngle, double gain) {
    Output.Movement movement = new Output.Movement();

    double distanceToTurn = targetAngle - yaw;
    float power = autoMinimum(autoClip((float) (distanceToTurn * gain / 90d)));

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

  private Output.Movement manualTurn(float gameStickRightX) {
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

  private Output.Movement manualStrafe(float gameStickLeftX) {
    Output.Movement movement = new Output.Movement();
    movement.frontLeftPower = -gameStickLeftX;
    movement.frontRightPower = gameStickLeftX;
    movement.rearLeftPower = gameStickLeftX;
    movement.rearRightPower = -gameStickLeftX;

    return movement;
  }

  private float arm(boolean dPadUp, boolean dPadDown, int armPosition) {
    if (dPadUp) {
      memory.autoMoveArm = false;
      return 0.25f;
    }

    if (dPadDown) {
      memory.autoMoveArm = false;
      return -0.25f;
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

  private float winch(boolean triangle, boolean cross) {
    if (triangle) {
      return 1f;
    }

    if (cross) {
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

  private float autoClip(float unclipped) {
    if (unclipped < -0.6f) {
      return -0.6f;
    }

    return Math.min(unclipped, 0.6f);
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

  // private float logistic(float offset) {
  //   float e = 2.71828f; // should be constant
  //   float k = -0.1f; // adjust to tune steepness of curve
  //   return 2 / (1 + Math.pow(e, k * offset);
  // }
}
