//package org.firstinspires.ftc.teamcode.old;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.Hardware;
//import org.firstinspires.ftc.teamcode.MoveData;
//
//public class ManualMoveController {
//    private final DcMotor leftFront;
//    private final DcMotor rightFront;
//    private final DcMotor leftBack;
//    private final DcMotor rightBack;
//    private final Gamepad gamepad;
//    private final Telemetry telemetry;
//
//    public ManualMoveController() {
//        this.leftFront = Hardware.leftFront;
//        this.rightFront = Hardware.rightFront;
//        this.leftBack = Hardware.leftBack;
//        this.rightBack = Hardware.rightBack;
//        this.gamepad = Hardware.gamepad1;
//        this.telemetry = Hardware.telemetry;
//    }
//
//    public void tick() {
//        MoveData movement = calculatePower();
//        leftFront.setPower(movement.frontLeftPower);
//        rightFront.setPower(movement.frontRightPower);
//        leftBack.setPower(movement.rearLeftPower);
//        rightBack.setPower(movement.rearRightPower);
//        telemetry.addData("frontLeftPower", movement.frontLeftPower);
//        telemetry.addData("frontRightPower", movement.frontRightPower);
//        telemetry.addData("rearLeftPower", movement.rearLeftPower);
//        telemetry.addData("rearRightPower", movement.rearRightPower);
//    }
//
//    private MoveData calculatePower() {
//        return manualTurn().add(manualMove(), manualStrafe());
//    }
//
//    private MoveData manualTurn() {
//        return MoveData.turn(-gamepad.right_stick_x, 0f, 1f);
//    }
//
//    private MoveData manualMove() {
//        return MoveData.straight(-gamepad.left_stick_y, 0f, 1f);
//    }
//
//    private MoveData manualStrafe() {
//        return MoveData.strafe(gamepad.left_stick_x, 0f, 1f);
//    }
//}
