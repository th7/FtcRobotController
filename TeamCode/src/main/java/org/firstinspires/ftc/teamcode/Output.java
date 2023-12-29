package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class Output {
  public float winchMotorPower = 0f;
  public float armMotorPower = 0f;
  public double topClawPosition = 0d;
  public double bottomClawPosition = 0d;
  public double launcherPosition = 0.4d;
  public Movement movement = new Movement();
  public ArrayList<Telemetry> telemetry = new ArrayList<>();

  public static class Movement {
    public float frontLeftPower = 0f;
    public float frontRightPower = 0f;
    public float rearLeftPower = 0f;
    public float rearRightPower = 0f;

    public static Movement move(float power, float min, float max) {
      Movement movement = new Movement();
      movement.move(power);
      movement.min(min);
      movement.max(max);
      return movement;
    }

    public static Movement turn(float power, float min, float max) {
      Movement movement = new Movement();
      movement.turn(power);
      movement.min(min);
      movement.max(max);
      return movement;
    }

    public static Movement strafe(float power, float min, float max) {
      Movement movement = new Movement();
      movement.strafe(power);
      movement.min(min);
      movement.max(max);
      return movement;
    }

    public Movement add(float min, float max, Movement... movements) {
      Movement movement = new Movement();

      movement.frontLeftPower = this.frontLeftPower;
      movement.frontRightPower = this.frontRightPower;
      movement.rearLeftPower = this.rearLeftPower;
      movement.rearRightPower = this.rearRightPower;

      for (Movement other : movements) {
        movement.frontLeftPower += other.frontLeftPower;
        movement.frontRightPower += other.frontRightPower;
        movement.rearLeftPower += other.rearLeftPower;
        movement.rearRightPower += other.rearRightPower;
      }

      movement.min(min);
      movement.max(max);
      return movement;
    }

    private void move(float power) {
      this.frontLeftPower = power;
      this.frontRightPower = power;
      this.rearLeftPower = power;
      this.rearRightPower = power;
    }

    private void turn(float power) {
      this.frontLeftPower = -power;
      this.frontRightPower = power;
      this.rearLeftPower = -power;
      this.rearRightPower = power;
    }

    private void strafe(float power) {
      this.frontLeftPower = -power;
      this.frontRightPower = power;
      this.rearLeftPower = power;
      this.rearRightPower = -power;
    }

    private void max(float max) {
      this.frontLeftPower = max(this.frontLeftPower, max);
      this.frontRightPower = max(this.frontRightPower, max);
      this.rearRightPower = max(this.rearRightPower, max);
      this.rearLeftPower = max(this.rearLeftPower, max);
    }

    private void min(float min) {
      this.frontLeftPower = min(this.frontLeftPower, min);
      this.frontRightPower = min(this.frontRightPower, min);
      this.rearRightPower = min(this.rearRightPower, min);
      this.rearLeftPower = min(this.rearLeftPower, min);
    }

    private float max(float unclipped, float max) {
      if (unclipped < -max) {
        return -max;
      }

      return Math.min(unclipped, max);
    }

    private float min(float unmin, float min) {
      if (-min < unmin && unmin < 0) {
        return -min;
      }

      if (0 < unmin && unmin < min) {
        return min;
      }

      return unmin;
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

  public void addTel(String name, Object value) {
    telemetry.add(new Telemetry(name, value));
  }
}
