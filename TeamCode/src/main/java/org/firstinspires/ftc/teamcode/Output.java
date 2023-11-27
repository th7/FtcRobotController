package org.firstinspires.ftc.teamcode;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

public class Output {

  public static class Movement {
    public float frontLeftPower = 0f;
    public float frontRightPower = 0f;
    public float rearLeftPower = 0f;
    public float rearRightPower = 0f;
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
  public double launcherPosition = 0.2d;

  public ArrayList<Telemetry> telemetry = new ArrayList<>();
}
