package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoArmController {
    private final DcMotor armMotor1;
    private final DcMotor armMotor2;
    private final Telemetry telemetry;

    private static final int straightUpPosition = 200;

    private int targetPosition = 0;

    AutoArmController(DcMotor armMotor1, DcMotor armMotor2, Telemetry telemetry) {
        this.armMotor1 = armMotor1;
        this.armMotor2 = armMotor2;
        this.telemetry = telemetry;
    }

    public void tick() {
        int distanceToMove = targetPosition - armMotor1.getCurrentPosition();
        double power = distanceToMove / 200d;
        telemetry.addData("armMotorPower", power);
        armMotor1.setPower(power);
        armMotor2.setPower(power);
    }


    public boolean inProgress() {
        return Util.closeEnough(this.targetPosition, armMotor1.getCurrentPosition(), 8);
    }

    public void straightUp() {
        this.targetPosition = straightUpPosition;
    }
}
