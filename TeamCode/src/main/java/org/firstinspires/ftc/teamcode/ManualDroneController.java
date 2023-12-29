package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ManualDroneController implements Tickable {
    private final Servo droneServo;
    private final Gamepad gamepad;
    private final Telemetry telemetry;

    private double launcherPosition = 0.4d;

    public ManualDroneController(Servo droneServo, Gamepad gamepad, Telemetry telemetry) {
        this.droneServo = droneServo;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void tick() {
        if (gamepad.circle) {
            this.launcherPosition = 0.7d;
        }

        droneServo.setPosition(this.launcherPosition);
        telemetry.addData("launcherPosition", this.launcherPosition);
    }
}
