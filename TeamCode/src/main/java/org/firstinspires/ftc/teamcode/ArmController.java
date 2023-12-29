package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ArmController {
    private final DcMotor armMotor1;
    private final DcMotor armMotor2;

    ArmController(DcMotor armMotor1, DcMotor armMotor2) {
        this.armMotor1 = armMotor1;
        this.armMotor2 = armMotor2;
    }
}
