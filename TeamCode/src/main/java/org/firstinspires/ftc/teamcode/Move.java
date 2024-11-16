package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Move {
    enum Mode {
        DRIVE_STRAIGHT, TURN, NONE, FOLLOW_APRIL_TAG;
    }
    private static DcMotor leftFront;
    private static DcMotor rightFront;
    private static DcMotor leftBack;
    private static DcMotor rightBack;
    private static AprilTagProcessor aprilTagProcessor;
    private static Telemetry telemetry;
    private static IMU imu;
    private static final double DESIRED_TAG_DISTANCE = 12.0;
    private static Move data;
    private MoveData moveData = new MoveData();
    private int targetPosition;
    private double targetHeading;
    private Mode mode = Mode.NONE;


    public static void init() {
        leftFront = Hardware.leftFront;
        rightFront = Hardware.rightFront;
        leftBack = Hardware.leftBack;
        rightBack = Hardware.rightBack;
        aprilTagProcessor = Hardware.aprilTagProcessor;
        telemetry = Hardware.telemetry;
        imu = Hardware.imu;
        data = new Move();
        telemetry.addData("Move.init()", true);
    }

    public static void calculateManual(float straightInput, float strafeInput, float turnInput) {
        MoveData straight = MoveData.straight(-straightInput, 0f, 1f);
        MoveData strafe = MoveData.strafe(strafeInput, 0f, 1f);
        MoveData turn = MoveData.turn(-turnInput, 0f, 1f);
        data.moveData = straight.add(strafe, turn);
    }

    public static void calculateAuto() {
        data.moveData = calculateMoveData();
        telemetry.addData("mode", data.mode);
        telemetry.addData("targetPosition", data.targetPosition);
        telemetry.addData("wheelPosition", position());
        telemetry.addData("targetHeading", data.targetHeading);
        telemetry.addData("yaw", yaw());
    }
    public static void tick() {
        leftFront.setPower(data.moveData.frontLeftPower);
        rightFront.setPower(data.moveData.frontRightPower);
        leftBack.setPower(data.moveData.rearLeftPower);
        rightBack.setPower(data.moveData.rearRightPower);
        telemetry.addData("frontLeftPower", data.moveData.frontLeftPower);
        telemetry.addData("frontRightPower", data.moveData.frontRightPower);
        telemetry.addData("rearLeftPower", data.moveData.rearLeftPower);
        telemetry.addData("rearRightPower", data.moveData.rearRightPower);
    }

    public static void followAprilTag() {
        data.mode = Mode.FOLLOW_APRIL_TAG;
    }

    private static MoveData calculateMoveData() {
        switch (data.mode) {
            case DRIVE_STRAIGHT: return driveStraight();
            case TURN: return turn();
            case FOLLOW_APRIL_TAG: return followAprilTagTick();
            default: return new MoveData();
        }
    }

    private static MoveData followAprilTagTick() {
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            double  rangeError      = (detection.ftcPose.range - DESIRED_TAG_DISTANCE);
            double  headingError    = detection.ftcPose.bearing - 0;
            double  yawError        = detection.ftcPose.yaw - 0;
            MoveData movement = MoveData.straight((float) rangeError, 0f, 1f);

//            // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
//            double  rangeError      = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
//            double  headingError    = desiredTag.ftcPose.bearing;
//            double  yawError        = desiredTag.ftcPose.yaw;
//
//            // Use the speed and turn "gains" to calculate how we want the robot to straight.
//            drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
//            turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
//            strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

            return movement;
        }

        return new MoveData();
    }

    public static boolean inProgress() {
        switch (data.mode) {
            case DRIVE_STRAIGHT: return driveStraightInProgress();
            case TURN: return turnInProgress();
            default: return false;
        }
    }

    public static boolean done() {
        return !inProgress();
    }

    public static void moveStraight(int distance) {
        data.mode = Mode.DRIVE_STRAIGHT;
        data.targetPosition = position() + distance;
        data.targetHeading = yaw();
    }

    public static void turn(double turnAmount) {
        data.mode = Mode.TURN;
        double targetAngle = data.targetHeading + turnAmount;

        if (targetAngle > 180) {
            data.targetHeading = targetAngle - 360;
        } else if (targetAngle < -180) {
            data.targetHeading = targetAngle + 360;
        } else {
            data.targetHeading = targetAngle;
        }
    }

    private static boolean driveStraightInProgress() {
        boolean done = closeEnough(position(), data.targetPosition, 8);
        if (done) { data.mode = Mode.NONE; }
        return !done;
    }

    private static boolean turnInProgress() {
        boolean done = closeEnough(yaw(), data.targetHeading, 1);
        if (done) { data.mode = Mode.NONE; }
        return !done;
    }

    private static boolean closeEnough(double a, double b, int threshold) {
        return Util.closeEnough(a,b,threshold);
    }

    private static MoveData driveStraight() {
        MoveData turnMovement = turnPower(5d);
        MoveData moveMovement = movePower();

        return turnMovement.add(moveMovement);
    }

    private static MoveData turn() {
        return turnPower(1d);
    }

    private static int position() {
        return leftFront.getCurrentPosition();
    }

    private static MoveData turnPower(double gain) {
        double distanceToTurn = data.targetHeading - yaw();
        double shortDistanceToTurn;

        if (distanceToTurn > 180) {
            shortDistanceToTurn = distanceToTurn - 360;
        } else if (distanceToTurn < -180) {
            shortDistanceToTurn = distanceToTurn + 360;
        } else {
            shortDistanceToTurn = distanceToTurn;
        }

        float power = (float) (shortDistanceToTurn * gain / 90d);

        return MoveData.turn(power, 0.05f, 0.6f);
    }

    private static double yaw() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    private static MoveData movePower() {
        int distanceToMove = data.targetPosition - position();
        float power = (float) distanceToMove / 200f;

        return MoveData.straight(power, 0.05f, 0.6f);
    }
}
