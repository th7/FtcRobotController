package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoClawController implements Tickable {
    private final Servo topClaw;
    private final Servo bottomClaw;
    private final ElapsedTime runtime;
    private final Telemetry telemetry;

    public static final double clawOpen = 0.25d;
    public static final double clawClosed = 0d;
    public final double clawWaitSeconds = 0.6d;

    public double topClawPosition = 0d;
    public double bottomClawPosition = 0d;
    public double clawMoveStartedSeconds = -1d;

    public AutoClawController(Servo topClaw, Servo bottomClaw, ElapsedTime runtime, Telemetry telemetry) {
        this.topClaw = topClaw;
        this.bottomClaw = bottomClaw;
        this.runtime = runtime;
        this.telemetry = telemetry;
    }

    public void tick() {
        topClaw.setPosition(this.topClawPosition);
        bottomClaw.setPosition(this.bottomClawPosition);
        telemetry.addData("topClawPosition", this.topClawPosition);
        telemetry.addData("bottomClawPosition", this.bottomClawPosition);
    }

    public boolean inProgress() {
        return runtime.seconds() < this.clawMoveStartedSeconds + this.clawWaitSeconds;
    }

    public void closeClaws() {
        this.topClawPosition = clawClosed;
        this.bottomClawPosition = clawClosed;
        this.clawMoveStartedSeconds = runtime.seconds();
    }

    public void openBottomClaw() {
        this.bottomClawPosition = clawOpen;
        this.clawMoveStartedSeconds = runtime.seconds();
    }

    public void openTopClaw() {
        this.topClawPosition = clawOpen;
        this.clawMoveStartedSeconds = runtime.seconds();
    }
}
