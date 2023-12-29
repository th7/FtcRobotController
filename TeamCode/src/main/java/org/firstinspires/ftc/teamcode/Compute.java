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
    output.armMotorPower = autoArmPower();
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

    output.armMotorPower = arm();
    output.movement = manualDrive();
    output.winchMotorPower = winch();

    manualClaw();
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
            () -> autoTurn(1d),
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

  private Output.Movement driveMovement() {
    Output.Movement turnMovement = autoTurn(5d);
    Output.Movement moveMovement = autoMove();

    return turnMovement.add(0f, 1f, moveMovement);
  }

  private Output.Movement manualDrive() {
    return manualTurn().add(0f, 1f, manualMove(), manualStrafe());
  }

  private void manualClaw() {
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

  private Output.Movement autoTurn(double gain) {
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
//    Output.Movement movement = new Output.Movement();
//    movement.frontLeftPower = gameStickRightX;
//    movement.frontRightPower = -gameStickRightX;
//    movement.rearLeftPower = gameStickRightX;
//    movement.rearRightPower = -gameStickRightX;
//    return movement;
    return Output.Movement.turn(input.gameStickRightX, 0f, 1f);
  }

  private Output.Movement manualMove() {
    return Output.Movement.move(-input.gameStickLeftY, 0f, 1f);
  }

  private Output.Movement manualStrafe() {
//    Output.Movement movement = new Output.Movement();
//    movement.frontLeftPower = -gameStickLeftX;
//    movement.frontRightPower = gameStickLeftX;
//    movement.rearLeftPower = gameStickLeftX;
//    movement.rearRightPower = -gameStickLeftX;
//
//    return movement;
    return Output.Movement.strafe(input.gameStickLeftX, 0f, 1f);
  }

  private float arm() {
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

  private float winch() {
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
}
