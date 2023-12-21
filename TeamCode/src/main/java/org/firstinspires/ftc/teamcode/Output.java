package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class Output {

  public static class Movement {
    public float frontLeftPower = 0f;
    public float frontRightPower = 0f;
    public float rearLeftPower = 0f;
    public float rearRightPower = 0f;

    public Movement add(Movement... others) {
      Movement movement = new Movement();

      movement.frontLeftPower = this.frontLeftPower;
      movement.frontRightPower = this.frontRightPower;
      movement.rearLeftPower = this.rearLeftPower;
      movement.rearRightPower = this.rearRightPower;

      for (Movement other : others) {
        movement.frontLeftPower += other.frontLeftPower;
        movement.frontRightPower += other.frontRightPower;
        movement.rearLeftPower += other.rearLeftPower;
        movement.rearRightPower += other.rearRightPower;
      }

      return movement;
    }

    public Movement clip(float max) {
      this.frontLeftPower = clip(this.frontLeftPower, max);
      this.frontRightPower = clip(this.frontRightPower, max);
      this.rearRightPower = clip(this.rearRightPower, max);
      this.rearLeftPower = clip(this.rearLeftPower, max);
      return this;
    }

    public Movement move(float power) {
      this.frontLeftPower = power;
      this.frontRightPower = power;
      this.rearLeftPower = power;
      this.rearRightPower = power;
      clip(1f);
      return this;
    }

    public Movement turn(float power) {
      this.frontLeftPower = -power;
      this.frontRightPower = power;
      this.rearLeftPower = -power;
      this.rearRightPower = power;
      clip(1f);
      return this;
    }

    public Movement strafe(float power) {
      this.frontLeftPower = -power;
      this.frontRightPower = power;
      this.rearLeftPower = power;
      this.rearRightPower = -power;
      clip(1f);
      return this;
    }

    private float clip(float unclipped, float max) {
      if (unclipped < -max) {
        return -max;
      }

      return Math.min(unclipped, max);
    }
  }
  public static class Telemetry {
    public String name;
    public Object value;

    Telemetry(String name, Object value) {
      this.name = name;
      this.value = value;
    }
  }
  public Movement movement = new Movement();
  public float winchMotorPower = 0f;

  public float armMotorPower = 0f;

  public double topClawPosition = 0d;
  public double bottomClawPosition = 0d;
  public double launcherPosition = 0.4d;

  public ArrayList<Telemetry> telemetry = new ArrayList<>();

  public void addTel(String name, Object value) {
    telemetry.add(new Telemetry(name, value));
  }
}
