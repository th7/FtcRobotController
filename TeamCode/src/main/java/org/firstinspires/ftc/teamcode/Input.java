package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class Input {
  public float gameStickLeftX = 0f;
  public float gameStickLeftY = 0f;
  public float gameStickRightX = 0f;
  public float gameStickRightY = 0f;

  public boolean dPadUp = false;
  public boolean dPadDown = false;
  public boolean dPadLeft = false;
  public boolean dPadRight = false;
  public boolean triangle = false;
  public boolean cross = false;
  public boolean circle = false;
  public float rightTrigger = 0f;
  public float leftTrigger = 0f;
  public boolean rightBumper = false;
  public boolean leftBumper = false;


  public int armPosition = 0;
  public int wheelPosition = 0;
  public double yaw = 0d;
  public double elapsedSeconds = 0;

  private Output.Telemetry tel(String name, Object value) {
    return new Output.Telemetry(name, value);
  }

  public ArrayList<Output.Telemetry> telemetry() {
    ArrayList<Output.Telemetry> telemetry = new ArrayList<>();
    telemetry.add(tel("gameStickLeftX", gameStickLeftX));
    telemetry.add(tel("gameStickLeftY", gameStickLeftY));
    telemetry.add(tel("gameStickRightX", gameStickRightX));
    telemetry.add(tel("gameStickRightY", gameStickRightY));

    telemetry.add(tel("dPadUp", dPadUp));
    telemetry.add(tel("dPadDown", dPadDown));
    telemetry.add(tel("dPadLeft", dPadLeft));
    telemetry.add(tel("dPadRight", dPadRight));
    telemetry.add(tel("triangle", triangle));
    telemetry.add(tel("cross", cross));
    telemetry.add(tel("circle", circle));
    telemetry.add(tel("rightTrigger", rightTrigger));
    telemetry.add(tel("leftTrigger", leftTrigger));
    telemetry.add(tel("rightBumper", rightBumper));
    telemetry.add(tel("leftBumper", leftBumper));

    telemetry.add(tel("armPosition", armPosition));
    telemetry.add(tel("wheelPosition", wheelPosition));
    telemetry.add(tel("yaw", yaw));
    telemetry.add(tel("elapsedSeconds", elapsedSeconds));

    return telemetry;
  }
}
