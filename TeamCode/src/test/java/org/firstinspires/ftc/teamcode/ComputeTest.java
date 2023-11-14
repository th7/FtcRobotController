package org.firstinspires.ftc.teamcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputeTest {
  Input input = new Input();
  Output output;

  public void compute() { output = Compute.compute(input); }

  public void computeAuto() { output = Compute.computeAutonomous(input); }

  @BeforeEach
  public void beforeEach() {
    Compute.memory = new Memory();
  }

  @Test
  public void defaults() {
    compute();
    // the last argument specifying an allowable delta of 0
    // may seem like nonsense, but it tricks java into agreeing
    // that 0 and -0 are in fact equal
    assertEquals(0f, output.movement.frontLeftPower, 0);
    assertEquals(0f, output.movement.frontRightPower, 0);
    assertEquals(0f, output.movement.rearLeftPower, 0);
    assertEquals(0f, output.movement.rearRightPower, 0);
  }

  @Test
  public void fullForward() {
    input.gameStickLeftY = -1f;
    compute();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void smallForward() {
    input.gameStickLeftY = -0.01f;
    compute();
    assertEquals(0.01f, output.movement.frontLeftPower);
    assertEquals(0.01f, output.movement.frontRightPower);
    assertEquals(0.01f, output.movement.rearLeftPower);
    assertEquals(0.01f, output.movement.rearRightPower);
  }

  @Test
  public void fullBackward() {
    input.gameStickLeftY = 1f;
    compute();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(-1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(-1f, output.movement.rearRightPower);
  }

  @Test
  public void smallBackward() {
    input.gameStickLeftY = 0.01f;
    compute();
    assertEquals(-0.01f, output.movement.frontLeftPower);
    assertEquals(-0.01f, output.movement.frontRightPower);
    assertEquals(-0.01f, output.movement.rearLeftPower);
    assertEquals(-0.01f, output.movement.rearRightPower);
  }

  @Test
  public void leftTurn() {
    input.gameStickRightX = -1f;
    compute();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void rightTurn() {
    input.gameStickRightX = 1f;
    compute();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(-1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(-1f, output.movement.rearRightPower);
  }

  @Test
  public void forwardLeftTurn() {
    input.gameStickLeftY = -1f;
    input.gameStickRightX = -1f;
    compute();
    assertEquals(0f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(0f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void forwardRightTurn() {
    input.gameStickLeftY = -1f;
    input.gameStickRightX = 1f;
    compute();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(0f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(0f, output.movement.frontRightPower);
  }

  @Test
  public void strafeLeft() {
    input.gameStickLeftX = -1f;
    compute();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(-1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void strafeRight() {
    input.gameStickLeftX = 1f;
    compute();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(-1f, output.movement.rearRightPower);
  }

  @Test
  public void forwardStrafeRightTurnLeft() {
    input.gameStickLeftY = -1f;
    input.gameStickLeftX = 1f;
    input.gameStickRightX = -1f;
    compute();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void armUp() {
    Compute.memory.autoMoveArm = true;
    input.dPadUp = true;
    compute();
    assertFalse(Compute.memory.autoMoveArm);
    assertEquals(0.25f, output.armMotorPower);
  }

  @Test
  public void armDown() {
    Compute.memory.autoMoveArm = true;
    input.dPadDown = true;
    compute();
    assertFalse(Compute.memory.autoMoveArm);
    assertEquals(-0.25f, output.armMotorPower);
  }

  @Test
  public void armUpAutoOffFast() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshold - 1;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armUpAutoOffSlow() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshold;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armDownAutoOffFast() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshold + 1;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armDownAutoOffSlow() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshold;
    Compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void setArmUp() {
    input.triangle = true;
    compute();
    assertTrue(Compute.memory.autoMoveArm);
    assertEquals(Compute.armUpPosition, Compute.memory.targetArmPosition);
  }

  @Test
  public void armUpFast() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshold - 1;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(Compute.armFast, output.armMotorPower);
  }

  @Test
  public void armUpSlow() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshold;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(Compute.armSlow, output.armMotorPower);
  }

  @Test
  public void setArmDown() {
    input.cross = true;
    compute();
    assertTrue(Compute.memory.autoMoveArm);
    assertEquals(Compute.armDownPosition, Compute.memory.targetArmPosition);
  }

  @Test
  public void armDownFast() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshold + 1;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(-Compute.armFast, output.armMotorPower);
  }

  @Test
  public void armDownSlow() {
    Compute.memory.targetArmPosition = Compute.armDownPosition;
    input.armPosition = Compute.armDownPosition + Compute.armSlowThreshold;
    Compute.memory.autoMoveArm = true;
    compute();
    assertEquals(-Compute.armSlow, output.armMotorPower);
  }

  @Test
  public void winchUp() {
    input.dPadRight = true;
    compute();
    assertEquals(0.25f, output.winchMotorPower);
  }

  @Test
  public void winchDown() {
    input.dPadLeft = true;
    compute();
    assertEquals(-0.25f, output.winchMotorPower);
  }

  @Test
  public void autoDriveForward() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 0;
    computeAuto();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveForwardSlow() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 150;
    computeAuto();
    assertEquals(0.5f, output.movement.frontLeftPower);
    assertEquals(0.5f, output.movement.frontRightPower);
    assertEquals(0.5f, output.movement.rearLeftPower);
    assertEquals(0.5f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveBackward() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 400;
    computeAuto();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(-1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(-1f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveStop() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 200;
    Compute.memory.currentStep = 99;
    computeAuto();
    // the last argument specifying an allowable delta of 0
    // may seem like nonsense, but it tricks java into agreeing
    // that 0 and -0 are in fact equal
    assertEquals(0f, output.movement.frontLeftPower, 0);
    assertEquals(0f, output.movement.frontRightPower, 0);
    assertEquals(0f, output.movement.rearLeftPower, 0);
    assertEquals(0f, output.movement.rearRightPower, 0);
  }

  @Test
  public void noCurrentStep() {
    assertEquals(0, Compute.memory.currentStep);
    computeAuto();
    assertEquals(1, Compute.memory.currentStep);
  }

  @Test
  public void moveNotComplete() {
    Compute.memory.currentStep = 1;
    Compute.memory.targetMovePosition = 400;
    input.wheelPosition = 200;
    computeAuto();
    assertEquals(1, Compute.memory.currentStep);
  }

  @Test
  public void stepOneComplete() {
    Compute.memory.currentStep = 1;
    computeAuto();
    assertEquals(2, Compute.memory.currentStep);
  }

  @Test
  public void stepOne() {
    input.wheelPosition = 0;
    Compute.memory.targetMovePosition = 0;
    Compute.runStep(1);
    assertEquals(1000, Compute.memory.targetMovePosition);
  }

  @Test
  public void autoTurnLeft() {
    Compute.memory.targetAngle = -90;
    input.yaw = 0;
    computeAuto();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void autoTurnPast180() {
    Compute.memory.targetAngle = 160;
    input.yaw = -160;
    computeAuto();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void turnNotComplete() {
    Compute.memory.currentStep = 1;
    Compute.memory.targetAngle = -90;
    input.yaw = 0;
    computeAuto();
    assertEquals(1, Compute.memory.currentStep);
  }

  @Test
  public void turnCloseEnoughToComplete() {
    Compute.memory.currentStep = 1;
    Compute.memory.targetAngle = -90;
    input.yaw = -90.1;
    computeAuto();
    assertEquals(2, Compute.memory.currentStep);
  }

  @Test
  public void turnLeftPast180() {
    Compute.memory.targetAngle = -175;
    Compute.turn(-10);
    assertEquals(175, Compute.memory.targetAngle);
  }

  @Test
  public void turnRightPast180() {
    Compute.memory.targetAngle = 175;
    Compute.turn(10);
    assertEquals(-175, Compute.memory.targetAngle);
  }

  @Test
  public void move() {
    Compute.memory.targetMovePosition = 900;
    Compute.move(100);
    assertEquals(1000, Compute.memory.targetMovePosition);
  }

  @Test
  public void manualCloseTopClaw() {
    Compute.memory.topClawPosition = 123d;
    input.leftBumper = true;
    compute();
    assertEquals(0d, Compute.memory.topClawPosition);
    assertEquals(0d, output.topClawPosition);
  }

  @Test
  public void manualCloseBottomClaw() {
    Compute.memory.bottomClawPosition = 123d;
    input.leftTrigger = 0.1f;
    compute();
    assertEquals(0d, Compute.memory.bottomClawPosition);
    assertEquals(0d, output.bottomClawPosition);
  }

  @Test
  public void manualOpenTopClaw() {
    input.rightBumper = true;
    compute();
    assertEquals(1d, Compute.memory.topClawPosition);
    assertEquals(1d, output.topClawPosition);
  }

  @Test
  public void manualKeepTopClawOpen() {
    Compute.memory.topClawPosition = 1d;
    compute();
    assertEquals(1d, output.topClawPosition);
  }

  @Test
  public void manualOpenBottomClaw() {
    input.rightTrigger = 0.1f;
    compute();
    assertEquals(1d, Compute.memory.bottomClawPosition);
    assertEquals(1d, output.bottomClawPosition);
  }

  @Test
  public void manualKeepBottomClawOpen() {
    Compute.memory.bottomClawPosition = 1d;
    compute();
    assertEquals(1d, output.bottomClawPosition);
  }

  @Test
  public void closeClawIfBothPressed() {
    input.leftBumper = true;
    input.rightBumper = true;
    input.leftTrigger = 0.1f;
    input.rightTrigger = 0.1f;
    compute();
    assertEquals(0d, Compute.memory.bottomClawPosition);
    assertEquals(0d, Compute.memory.topClawPosition);
  }

  @Test
  public void autoCloseTopClaw() {
    Compute.memory.topClawPosition = 0d;
    computeAuto();
    assertEquals(0d, output.topClawPosition);
  }

  @Test
  public void autoCloseBottomClaw() {
    Compute.memory.bottomClawPosition = 0d;
    computeAuto();
    assertEquals(0d, output.bottomClawPosition);
  }

  @Test
  public void autoOpenTopClaw() {
    Compute.memory.topClawPosition = 1d;
    computeAuto();
    assertEquals(1d, output.topClawPosition);
  }

  @Test
  public void autoOpenBottomClaw() {
    Compute.memory.bottomClawPosition = 1d;
    computeAuto();
    assertEquals(1d, output.bottomClawPosition);
  }

  @Test
  public void autoArm() {
    Compute.memory.targetArmPosition = Compute.armUpPosition;
    input.armPosition = Compute.armUpPosition - Compute.armSlowThreshold - 1;
    computeAuto();
    assertEquals(Compute.armFast, output.armMotorPower);
  }
}
