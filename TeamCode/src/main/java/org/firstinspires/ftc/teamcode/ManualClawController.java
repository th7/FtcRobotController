package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.AutoClawController.clawClosed;
import static org.firstinspires.ftc.teamcode.AutoClawController.clawOpen;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ManualClawController implements Tickable {
    private final Servo topClaw;
    private final Servo bottomClaw;
    private final Gamepad gamepad;
    private final Telemetry telemetry;

    public double topClawPosition = 0d;
    public double bottomClawPosition = 0d;

    public ManualClawController(Servo topClaw, Servo bottomClaw, Gamepad gamepad, Telemetry telemetry) {
        this.topClaw = topClaw;
        this.bottomClaw = bottomClaw;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void tick() {
        if (gamepad.right_bumper) {
            this.topClawPosition = clawClosed;
        }
        if (gamepad.left_bumper) {
            this.topClawPosition = clawOpen;
        }
        if (gamepad.right_trigger > 0) {
            this.bottomClawPosition = clawClosed;
        }
        if (gamepad.left_trigger > 0) {
            this.bottomClawPosition = clawOpen;
        }

        topClaw.setPosition(this.topClawPosition);
        bottomClaw.setPosition(this.bottomClawPosition);

        telemetry.addData("topClawPosition", this.topClawPosition);
        telemetry.addData("bottomClawPosition", this.bottomClawPosition);
    }
}
