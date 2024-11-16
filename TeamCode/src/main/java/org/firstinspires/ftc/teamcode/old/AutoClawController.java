//package org.firstinspires.ftc.teamcode.old;
//
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.Hardware;
//
//public class AutoClawController {
//    private final Servo topClaw;
//    private final Servo bottomClaw;
//    private final ElapsedTime runtime;
//    private final Telemetry telemetry;
//
//    public static final double clawOpen = 0.25d;
//    public static final double clawClosed = 0d;
//    public final double clawWaitSeconds = 0.6d;
//
//    public double topClawPosition = 0d;
//    public double bottomClawPosition = 0d;
//    public double clawMoveStartedSeconds = -1d;
//
//    public AutoClawController() {
//        this.topClaw = Hardware.topClaw;
//        this.bottomClaw = Hardware.bottomClaw;
//        this.runtime = Hardware.runtime;
//        this.telemetry = Hardware.telemetry;
//    }
//
//    public void tick() {
//        topClaw.setPosition(this.topClawPosition);
//        bottomClaw.setPosition(this.bottomClawPosition);
//        telemetry.addData("topClawPosition", this.topClawPosition);
//        telemetry.addData("bottomClawPosition", this.bottomClawPosition);
//        telemetry.addData("ClawDone", this.done());
//    }
//
//    public boolean inProgress() {
//        return runtime.seconds() < this.clawMoveStartedSeconds + this.clawWaitSeconds;
//    }
//
//    public void closeClaws() {
//        this.topClawPosition = clawClosed;
//        this.bottomClawPosition = clawClosed;
//        this.clawMoveStartedSeconds = runtime.seconds();
//    }
//
//    public void openBottomClaw() {
//        this.bottomClawPosition = clawOpen;
//        this.clawMoveStartedSeconds = runtime.seconds();
//    }
//
//    public void openTopClaw() {
//        this.topClawPosition = clawOpen;
//        this.clawMoveStartedSeconds = runtime.seconds();
//    }
//
//    public boolean done() {
//        return !inProgress();
//    }
//}
