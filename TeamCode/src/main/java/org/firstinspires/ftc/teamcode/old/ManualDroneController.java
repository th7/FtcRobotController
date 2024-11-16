//package org.firstinspires.ftc.teamcode.old;
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//public class ManualDroneController {
//    public static final double initialPosition = 0.4d;
//    private final Servo droneServo;
//    private final Gamepad gamepad;
//    private final Telemetry telemetry;
//
//    private double launcherPosition = initialPosition;
//
//    public ManualDroneController(Servo droneServo, Gamepad gamepad, Telemetry telemetry) {
//        this.droneServo = droneServo;
//        this.gamepad = gamepad;
//        this.telemetry = telemetry;
//    }
//
//    public void tick() {
//        if (gamepad.circle) {
//            this.launcherPosition = 0.7d;
//        }
//
//        droneServo.setPosition(this.launcherPosition);
//        telemetry.addData("launcherPosition", this.launcherPosition);
//    }
//}
