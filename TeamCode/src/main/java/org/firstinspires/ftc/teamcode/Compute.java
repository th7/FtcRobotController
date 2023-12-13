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

  public final double autoMinimumPower = 0.5d;

  public Memory memory;
  public Input input;

  Compute(Memory memory, Input input) {
    this.memory = memory;
    this.input = input;
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

  public Output teamProp() {
    return computeAutonomous(this::runTeamPropStep);
  }

  public Output backstageRed() {
    return computeAutonomous(this::runBackstageRedStep);
  }

  public Output backstageBlue() {
    return computeAutonomous(this::runBackstageBlueStep);
  }

  public Output frontstageBlue() {
    return computeAutonomous(this::runFrontstageBlueStep);
  }

  public Output frontstageRed() {
    return computeAutonomous(this::runFrontstageRedStep);
  }

  interface StepCallback {
    void call();
  }
  private void runTeamPropStep() {
    switch(memory.currentStep + 1) {
      case 1: openClaws(); break;
      case 2: waitAfterClaws(); break;
      case 3: moveToSpikeMark(); break;
      case 4: openBottomClaw(); break;
      case 5: waitAfterClaws(); break;
      case 6: moveBackFromSpikeMarks(); break;
      default: stop();
    }
  }

  private void openClaws() {
    memory.topClawPosition = clawClosed;
    memory.bottomClawPosition = clawClosed;
  }

  private void waitAfterClaws() {
    waitFor(0.6);
  }

  private void moveToSpikeMark() {
    move(oneTile * 1.3);
  }

  private void openBottomClaw() {
    memory.bottomClawPosition = clawOpen;
  }

  private void moveBackFromSpikeMarks() {
     move(-oneTile * 0.9);
  }

  private void runBackstageRedStep() {
    switch(memory.currentStep + 1) {
      case 1: openClaws(); break;
      case 2: waitAfterClaws(); break;
      case 3: moveToSpikeMark(); break;
      case 4: openBottomClaw(); break;
      case 5: waitAfterClaws(); break;
      case 6: moveBackFromSpikeMarks(); break;
      case 7: turn(turnRight); break;
      case 8: toBackstageShort(); break;
      case 9: openTopClaw(); break;
      case 10: waitAfterClaws(); break;
      case 11: nudgeBack(); break;
      default: stop(); break;
    }
  }

  private void toBackstageShort() {
    move(oneTile * 1.8);
  }

  private void openTopClaw() {
    memory.topClawPosition = clawOpen;
  }

  private void nudgeBack() {
    move(-oneTile * 0.2);
  }

  private void runBackstageBlueStep() {
    switch(memory.currentStep + 1) {
      case 1: openClaws(); break;
      case 2: waitAfterClaws(); break;
      case 3: moveToSpikeMark(); break;
      case 4: openBottomClaw(); break;
      case 5: waitAfterClaws(); break;
      case 6: moveBackFromSpikeMarks(); break;
      case 7: turn(turnLeft); break;
      case 8: toBackstageShort(); break;
      case 9: openTopClaw(); break;
      case 10: waitAfterClaws(); break;
      case 11: nudgeBack(); break;
      default: stop(); break;
    }
  }

  private void runFrontstageBlueStep() {
    switch(memory.currentStep + 1) {
      case 1: openClaws(); break;
      case 2: waitAfterClaws(); break;
      case 3: moveToSpikeMark(); break;
      case 4: openBottomClaw(); break;
      case 5: waitAfterClaws(); break;
      case 6: moveBackFromSpikeMarks(); break;
      case 7: turn(turnRight); break;
      case 8: moveToWing(); break;
      case 9: turn(turnLeft); break;
      case 10: wingToMiddle(); break;
      case 11: turn(45); break;
      case 12: moveToMiddle(); break;
      case 13: turn(45); break;
      case 14: moveBackstageLong(); break;
      case 15: openTopClaw(); break;
      case 16: nudgeBack(); break;
      default: stop();
    }
  }

  private void moveToWing() {
    move(oneTile * 0.8);
  }

  private void wingToMiddle() {
    move(oneTile * 1.7);
  }

  private void moveToMiddle() {
    move(oneTile * 0.8);
  }

  private void moveBackstageLong() {
    move(oneTile * 4);
  }

  private void runFrontstageRedStep() {
    switch(memory.currentStep + 1) {
      case 1: openClaws(); break;
      case 2: waitAfterClaws(); break;
      case 3: moveToSpikeMark(); break;
      case 4: openBottomClaw(); break;
      case 5: waitAfterClaws(); break;
      case 6: moveBackFromSpikeMarks(); break;
      case 7: turn(turnLeft); break;
      case 8: moveToWing(); break;
      case 9: turn(turnRight); break;
      case 10: wingToMiddle(); break;
      case 11: turn(-45); break;
      case 12: moveToMiddle(); break;
      case 13: turn(-45); break;
      case 14: moveBackstageLong(); break;
      case 15: openTopClaw(); break;
      case 16: nudgeBack(); break;
      default: stop();
    }
  }


  private Output computeAutonomous(StepCallback stepCallback) {
    Output output = new Output();

    if (!inProgress(input.wheelPosition, input.yaw)) {
      stepCallback.call();
      memory.currentStep += 1;
      output.addTel("inProgress", false);
    } else {
      output.addTel("inProgress", true);
    }

    output.addTel("currentStep", memory.currentStep);

    output.topClawPosition = memory.topClawPosition;
    output.bottomClawPosition = memory.bottomClawPosition;
    output.armMotorPower = autoArmPower(input.armPosition);

    if (input.elapsedSeconds < memory.targetWaitSeconds) {
      return output;
    }

    if (memory.currentlyTurning) {
      output.movement = autoTurn(input.yaw, memory.targetAngle, 1d);
    } else if (memory.currentlyDriving) {
      Output.Movement turnMovement = autoTurn(input.yaw, memory.targetAngle, 5d);
      Output.Movement moveMovement = autoMove(input.wheelPosition, memory.targetMovePosition);

      output.movement.frontLeftPower = clip(turnMovement.frontLeftPower + moveMovement.frontLeftPower);
      output.movement.frontRightPower = clip(turnMovement.frontRightPower + moveMovement.frontRightPower);
      output.movement.rearLeftPower = clip(turnMovement.rearLeftPower + moveMovement.rearLeftPower);
      output.movement.rearRightPower = clip(turnMovement.rearRightPower + moveMovement.rearRightPower);
    }

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

  private float arm(boolean dPadUp, boolean dPadDown, int armPosition) {
    if (dPadUp) {
      memory.autoMoveArm = false;
      return 0.25f;
    }

    if (dPadDown) {
      memory.autoMoveArm = false;
      return -0.25f;
    }

//    if (triangle) {
//      memory.autoMoveArm = true;
//      memory.targetArmPosition = armUpPosition;
//    }
//    if (cross) {
//      memory.autoMoveArm = true;
//      memory.targetArmPosition = armDownPosition;
//    }

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

    if (unclipped > 0.6f) {
      return 0.6f;
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
    }
    memory.currentStep = stepNumber;
  }

  public boolean inProgress(int wheelPosition, double yaw) {
    if (memory.currentlyTurning) {
      return inProgressTurn(yaw);
    }

    return inProgressMove(wheelPosition) || inProgressTurn(yaw);
  }

  public boolean inProgressMove(int wheelPosition) {
    return !moveCloseEnough(memory.targetMovePosition, wheelPosition);
  }

  public boolean inProgressTurn(double yaw) {
    return !turnCloseEnough(memory.targetAngle, yaw);
  }


  public boolean turnCloseEnough(double a, double b) {
    double difference = Math.abs(a - b);
    return difference <= 1;
  }

  public boolean moveCloseEnough(double a, double b) {
    double difference = Math.abs(a -b);
    return difference <= 8;
  }

  public void step1() {
    move(2000);
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
    memory.currentlyTurning = true;
    memory.currentlyDriving = false;
  }

  public void move(int moveAmount) {
    memory.targetMovePosition = input.wheelPosition + moveAmount;
    memory.currentlyDriving = true;
    memory.currentlyTurning = false;
  }

  public void move(double moveAmount) {
    move((int) moveAmount);
  }

  public void stop() {
    memory.currentlyDriving = false;
    memory.currentlyTurning = false;
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
