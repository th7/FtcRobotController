package org.firstinspires.ftc.teamcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputeTest {
  Input input = new Input();
  Memory memory = new Memory();
  Output output;
  Compute compute = new Compute(memory);

  public void compute() { output = compute.teleOp(); }

  public void computeAuto() { output = compute.computeAutonomous(); }
  @BeforeEach
  public void setup() { compute.input = input; }
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
    compute.memory.autoMoveArm = true;
    input.dPadUp = true;
    compute();
    assertFalse(compute.memory.autoMoveArm);
    assertEquals(0.25f, output.armMotorPower);
  }

  @Test
  public void armDown() {
    compute.memory.autoMoveArm = true;
    input.dPadDown = true;
    compute();
    assertFalse(compute.memory.autoMoveArm);
    assertEquals(-0.25f, output.armMotorPower);
  }

  @Test
  public void armUpAutoOffFast() {
    compute.memory.targetArmPosition = compute.armUpPosition;
    input.armPosition = compute.armUpPosition - compute.armSlowThreshold - 1;
    compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armUpAutoOffSlow() {
    compute.memory.targetArmPosition = compute.armUpPosition;
    input.armPosition = compute.armUpPosition - compute.armSlowThreshold;
    compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armDownAutoOffFast() {
    compute.memory.targetArmPosition = compute.armDownPosition;
    input.armPosition = compute.armDownPosition + compute.armSlowThreshold + 1;
    compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void armDownAutoOffSlow() {
    compute.memory.targetArmPosition = compute.armDownPosition;
    input.armPosition = compute.armDownPosition + compute.armSlowThreshold;
    compute.memory.autoMoveArm = false;
    compute();
    assertEquals(0f, output.armMotorPower);
  }

  @Test
  public void setArmUp() {
    input.triangle = true;
    compute();
    assertTrue(compute.memory.autoMoveArm);
    assertEquals(compute.armUpPosition, compute.memory.targetArmPosition);
  }

  @Test
  public void armUpFast() {
    compute.memory.targetArmPosition = compute.armUpPosition;
    input.armPosition = compute.armUpPosition - compute.armSlowThreshold - 1;
    compute.memory.autoMoveArm = true;
    compute();
    assertEquals(compute.armFast, output.armMotorPower);
  }

  @Test
  public void armUpSlow() {
    compute.memory.targetArmPosition = compute.armUpPosition;
    input.armPosition = compute.armUpPosition - compute.armSlowThreshold;
    compute.memory.autoMoveArm = true;
    compute();
    assertEquals(compute.armSlow, output.armMotorPower);
  }

  @Test
  public void setArmDown() {
    input.cross = true;
    compute();
    assertTrue(compute.memory.autoMoveArm);
    assertEquals(compute.armDownPosition, compute.memory.targetArmPosition);
  }

  @Test
  public void armDownFast() {
    compute.memory.targetArmPosition = compute.armDownPosition;
    input.armPosition = compute.armDownPosition + compute.armSlowThreshold + 1;
    compute.memory.autoMoveArm = true;
    compute();
    assertEquals(-compute.armFast, output.armMotorPower);
  }

  @Test
  public void armDownSlow() {
    compute.memory.targetArmPosition = compute.armDownPosition;
    input.armPosition = compute.armDownPosition + compute.armSlowThreshold;
    compute.memory.autoMoveArm = true;
    compute();
    assertEquals(-compute.armSlow, output.armMotorPower);
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
    compute.memory.targetMovePosition = 200;
    input.wheelPosition = 0;
    computeAuto();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveForwardMinimum() {
    compute.memory.targetMovePosition = 1;
    input.wheelPosition = 0;
    computeAuto();
    assertEquals(0.05f, output.movement.frontLeftPower);
    assertEquals(0.05f, output.movement.frontRightPower);
    assertEquals(0.05f, output.movement.rearLeftPower);
    assertEquals(0.05f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveForwardSlow() {
    compute.memory.targetMovePosition = 200;
    input.wheelPosition = 150;
    computeAuto();
    assertEquals(0.5f, output.movement.frontLeftPower);
    assertEquals(0.5f, output.movement.frontRightPower);
    assertEquals(0.5f, output.movement.rearLeftPower);
    assertEquals(0.5f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveBackward() {
    compute.memory.targetMovePosition = 200;
    input.wheelPosition = 400;
    computeAuto();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(-1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(-1f, output.movement.rearRightPower);
  }

  @Test
  public void autoDriveStop() {
    compute.memory.targetMovePosition = 200;
    input.wheelPosition = 200;
    compute.memory.currentStep = 99;
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
    assertEquals(0, compute.memory.currentStep);
    computeAuto();
    assertEquals(1, compute.memory.currentStep);
  }

  @Test
  public void moveNotComplete() {
    compute.memory.currentStep = 1;
    compute.memory.targetMovePosition = 400;
    input.wheelPosition = 200;
    computeAuto();
    assertEquals(1, compute.memory.currentStep);
  }

  @Test
  public void stepOneComplete() {
    compute.memory.currentStep = 1;
    computeAuto();
    assertEquals(2, compute.memory.currentStep);
  }

  @Test
  public void autoTurnLeft() {
    compute.memory.targetAngle = 90;
    input.yaw = 0;
    computeAuto();
    assertEquals(-0.5f, output.movement.frontLeftPower);
    assertEquals(0.5f, output.movement.frontRightPower);
    assertEquals(-0.5f, output.movement.rearLeftPower);
    assertEquals(0.5f, output.movement.rearRightPower);
  }

  @Test
  public void autoTurnLeftMinumum() {
    compute.memory.targetAngle = 1;
    input.yaw = 0;
    computeAuto();
    assertEquals(-0.05f, output.movement.frontLeftPower);
    assertEquals(0.05f, output.movement.frontRightPower);
    assertEquals(-0.05f, output.movement.rearLeftPower);
    assertEquals(0.05f, output.movement.rearRightPower);
  }

  @Test
  public void autoTurnRightMinumum() {
    compute.memory.targetAngle = -1;
    input.yaw = 0;
    computeAuto();
    assertEquals(0.05f, output.movement.frontLeftPower);
    assertEquals(-0.05f, output.movement.frontRightPower);
    assertEquals(0.05f, output.movement.rearLeftPower);
    assertEquals(-0.05f, output.movement.rearRightPower);
  }

  @Test
  public void autoTurnLeftPast180() {
    compute.memory.targetAngle = -160;
    input.yaw = 160;
    computeAuto();
    assertEquals(-1f, output.movement.frontLeftPower);
    assertEquals(1f, output.movement.frontRightPower);
    assertEquals(-1f, output.movement.rearLeftPower);
    assertEquals(1f, output.movement.rearRightPower);
  }

  @Test
  public void autoTurnRightPast180() {
    compute.memory.targetAngle = 160;
    input.yaw = -160;
    computeAuto();
    assertEquals(1f, output.movement.frontLeftPower);
    assertEquals(-1f, output.movement.frontRightPower);
    assertEquals(1f, output.movement.rearLeftPower);
    assertEquals(-1f, output.movement.rearRightPower);
  }

  @Test
  public void turnNotComplete() {
    compute.memory.currentStep = 1;
    compute.memory.targetAngle = -90;
    input.yaw = 0;
    computeAuto();
    assertEquals(1, compute.memory.currentStep);
  }

  @Test
  public void turnCloseEnoughToComplete() {
    compute.memory.currentStep = 1;
    compute.memory.targetAngle = -90;
    input.yaw = -90.1;
    computeAuto();
    assertEquals(2, compute.memory.currentStep);
  }

  @Test
  public void turnLeftPast180() {
    compute.memory.targetAngle = -175;
    compute.turn(-10);
    assertEquals(175, compute.memory.targetAngle);
  }

  @Test
  public void turnRightPast180() {
    compute.memory.targetAngle = 175;
    compute.turn(10);
    assertEquals(-175, compute.memory.targetAngle);
  }

  @Test
  public void move() {
    compute.memory.targetMovePosition = 900;
    compute.move(100);
    assertEquals(1000, compute.memory.targetMovePosition);
  }

  @Test
  public void manualCloseTopClaw() {
    compute.memory.topClawPosition = 123d;
    input.leftBumper = true;
    compute();
    assertEquals(0d, compute.memory.topClawPosition);
    assertEquals(0d, output.topClawPosition);
  }

  @Test
  public void manualCloseBottomClaw() {
    compute.memory.bottomClawPosition = 123d;
    input.leftTrigger = 0.1f;
    compute();
    assertEquals(0d, compute.memory.bottomClawPosition);
    assertEquals(0d, output.bottomClawPosition);
  }

  @Test
  public void manualOpenTopClaw() {
    input.rightBumper = true;
    compute();
    assertEquals(1d, compute.memory.topClawPosition);
    assertEquals(1d, output.topClawPosition);
  }

  @Test
  public void manualKeepTopClawOpen() {
    compute.memory.topClawPosition = 1d;
    compute();
    assertEquals(1d, output.topClawPosition);
  }

  @Test
  public void manualOpenBottomClaw() {
    input.rightTrigger = 0.1f;
    compute();
    assertEquals(1d, compute.memory.bottomClawPosition);
    assertEquals(1d, output.bottomClawPosition);
  }

  @Test
  public void manualKeepBottomClawOpen() {
    compute.memory.bottomClawPosition = 1d;
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
    assertEquals(0d, compute.memory.bottomClawPosition);
    assertEquals(0d, compute.memory.topClawPosition);
  }

  @Test
  public void autoCloseTopClaw() {
    compute.memory.topClawPosition = 0d;
    computeAuto();
    assertEquals(0d, output.topClawPosition);
  }

  @Test
  public void autoCloseBottomClaw() {
    compute.memory.bottomClawPosition = 0d;
    computeAuto();
    assertEquals(0d, output.bottomClawPosition);
  }

  @Test
  public void autoOpenTopClaw() {
    compute.memory.topClawPosition = 1d;
    computeAuto();
    assertEquals(1d, output.topClawPosition);
  }

  @Test
  public void autoOpenBottomClaw() {
    compute.memory.bottomClawPosition = 1d;
    computeAuto();
    assertEquals(1d, output.bottomClawPosition);
  }

  @Test
  public void autoArm() {
    compute.memory.targetArmPosition = compute.armUpPosition;
    input.armPosition = compute.armUpPosition - compute.armSlowThreshold - 1;
    computeAuto();
    assertEquals(compute.armFast, output.armMotorPower);
  }
}
