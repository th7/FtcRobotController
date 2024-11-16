//package org.firstinspires.ftc.teamcode.old;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.Hardware;
//import org.firstinspires.ftc.teamcode.Util;
//
//public class AutoArmController {
//    private final DcMotor liftMotor;
//    private final Telemetry telemetry;
//
//    private static final int straightUpPosition = 200;
//
//    private int targetPosition = 0;
//
//    AutoArmController() {
//        this.liftMotor = Hardware.liftMotor;
//        this.telemetry = Hardware.telemetry;
//    }
//
//    public void tick() {
//        int distanceToMove = targetPosition - liftMotor.getCurrentPosition();
//        double power = distanceToMove / 200d;
//        telemetry.addData("armMotorPower", power);
//        liftMotor.setPower(power);
//    }
//
//
//    public boolean inProgress() {
//        return Util.closeEnough(this.targetPosition, liftMotor.getCurrentPosition(), 8);
//    }
//
//    public void straightUp() {
//        this.targetPosition = straightUpPosition;
//    }
//}
