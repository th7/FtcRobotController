package org.firstinspires.ftc.teamcode;

public class Output {
  public static class Movement {
    public float frontLeftPower = 0f;
    public float frontRightPower = 0f;
    public float rearLeftPower = 0f;
    public float rearRightPower = 0f;
  }
  public Movement movement = new Movement();
  public float winchMotorPower = 0f;

  public float armMotorPower = 0f;

  public double topClawPosition = 0d;
  public double bottomClawPosition = 0d;
}
