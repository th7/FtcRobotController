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
    assertEquals(0f, output.frontLeftPower, 0);
    assertEquals(0f, output.frontRightPower, 0);
    assertEquals(0f, output.rearLeftPower, 0);
    assertEquals(0f, output.rearRightPower, 0);
  }

  @Test
  public void fullForward() {
    input.gameStickLeftY = -1f;
    Compute.memory.autoDrive = true;
    compute();
    assertFalse(Compute.memory.autoDrive);
    assertEquals(1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void smallForward() {
    input.gameStickLeftY = -0.01f;
    compute();
    assertEquals(0.01f, output.frontLeftPower);
    assertEquals(0.01f, output.frontRightPower);
    assertEquals(0.01f, output.rearLeftPower);
    assertEquals(0.01f, output.rearRightPower);
  }

  @Test
  public void fullBackward() {
    input.gameStickLeftY = 1f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void smallBackward() {
    input.gameStickLeftY = 0.01f;
    compute();
    assertEquals(-0.01f, output.frontLeftPower);
    assertEquals(-0.01f, output.frontRightPower);
    assertEquals(-0.01f, output.rearLeftPower);
    assertEquals(-0.01f, output.rearRightPower);
  }

  @Test
  public void leftTurn() {
    input.gameStickRightX = -1f;
    Compute.memory.autoDrive = true;
    compute();
    assertFalse(Compute.memory.autoDrive);
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void rightTurn() {
    input.gameStickRightX = 1f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void forwardLeftTurn() {
    input.gameStickLeftY = -1f;
    input.gameStickRightX = -1f;
    compute();
    assertEquals(0f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(0f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void forwardRightTurn() {
    input.gameStickLeftY = -1f;
    input.gameStickRightX = 1f;
    compute();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(0f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(0f, output.frontRightPower);
  }

  @Test
  public void strafeLeft() {
    input.gameStickLeftX = -1f;
    Compute.memory.autoDrive = true;
    compute();
    assertFalse(Compute.memory.autoDrive);
    assertEquals(1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void strafeRight() {
    input.gameStickLeftX = 1f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void forwardStrafeRightTurnLeft() {
    input.gameStickLeftY = -1f;
    input.gameStickLeftX = 1f;
    input.gameStickRightX = -1f;
    compute();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
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
  public void setAutoMoveForward() {
    input.rightTrigger = 0.1f;
    input.wheelPosition = 123;
    compute();
    assertTrue(Compute.memory.autoDrive);
    assertEquals(323, Compute.memory.targetMovePosition);
  }

  @Test
  public void autoDriveForward() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 0;
    Compute.memory.autoDrive = true;
    computeAuto();
    assertEquals(1f, output.frontLeftPower);
    assertEquals(1f, output.frontRightPower);
    assertEquals(1f, output.rearLeftPower);
    assertEquals(1f, output.rearRightPower);
  }

  @Test
  public void autoDriveForwardSlow() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 150;
    Compute.memory.autoDrive = true;
    computeAuto();
    assertEquals(0.5f, output.frontLeftPower);
    assertEquals(0.5f, output.frontRightPower);
    assertEquals(0.5f, output.rearLeftPower);
    assertEquals(0.5f, output.rearRightPower);
  }

  @Test
  public void autoDriveBackward() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 400;
    Compute.memory.autoDrive = true;
    computeAuto();
    assertEquals(-1f, output.frontLeftPower);
    assertEquals(-1f, output.frontRightPower);
    assertEquals(-1f, output.rearLeftPower);
    assertEquals(-1f, output.rearRightPower);
  }

  @Test
  public void autoDriveStop() {
    Compute.memory.targetMovePosition = 200;
    input.wheelPosition = 200;
    Compute.memory.autoDrive = true;
    computeAuto();
    // the last argument specifying an allowable delta of 0
    // may seem like nonsense, but it tricks java into agreeing
    // that 0 and -0 are in fact equal
    assertEquals(0f, output.frontLeftPower, 0);
    assertEquals(0f, output.frontRightPower, 0);
    assertEquals(0f, output.rearLeftPower, 0);
    assertEquals(0f, output.rearRightPower, 0);
  }
}
