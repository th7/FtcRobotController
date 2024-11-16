package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class BaseOp extends OpMode {
  @Override
  public void init() {
    Hardware.hardwareMap = hardwareMap;
    Hardware.telemetry = telemetry;
    Hardware.init();
    Arm.init();
    Move.init();
    telemetry.addData("BaseOp.init()", true);
  }

  @Override
  public void init_loop() {}

  @Override
  public void start() {
    Hardware.runtime.reset();
  }

  @Override
  public void stop() {}
}