//package org.firstinspires.ftc.teamcode.old;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.teamcode.Hardware;
//import org.firstinspires.ftc.teamcode.MoveData;
//import org.firstinspires.ftc.teamcode.Util;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//import java.util.List;
//
//public class AutoMoveController {
//
//    enum Mode {
//        DRIVE_STRAIGHT, TURN, NONE, FOLLOW_APRIL_TAG;
//    }
//    private final DcMotor leftFront;
//    private final DcMotor rightFront;
//    private final DcMotor leftBack;
//    private final DcMotor rightBack;
//    private final IMU imu;
//    private final Telemetry telemetry;
//    private VisionPortal visionPortal;               // Used to manage the video source.
//    private AprilTagProcessor aprilTagProcessor;              // Used for managing the AprilTag detection process.
//    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag
//    private int targetPosition;
//    private double targetHeading;
//    private Mode mode = Mode.NONE;
//    final double DESIRED_DISTANCE = 12.0;
//
//    public AutoMoveController() {
//        this.leftFront = Hardware.leftFront;
//        this.rightFront = Hardware.rightFront;
//        this.leftBack = Hardware.leftBack;
//        this.rightBack = Hardware.rightBack;
//        this.imu = Hardware.imu;
//        this.aprilTagProcessor = Hardware.aprilTagProcessor;
//        this.telemetry = Hardware.telemetry;
//    }
//
//    public void tick() {
//        MoveData movement = calculatePower();
//        leftFront.setPower(movement.frontLeftPower);
//        rightFront.setPower(movement.frontRightPower);
//        leftBack.setPower(movement.rearLeftPower);
//        rightBack.setPower(movement.rearRightPower);
//        telemetry.addData("targetPosition", this.targetPosition);
//        telemetry.addData("wheelPosition", position());
//        telemetry.addData("targetHeading", this.targetHeading);
//        telemetry.addData("yaw", yaw());
//        telemetry.addData("frontLeftPower", movement.frontLeftPower);
//        telemetry.addData("frontRightPower", movement.frontRightPower);
//        telemetry.addData("rearLeftPower", movement.rearLeftPower);
//        telemetry.addData("rearRightPower", movement.rearRightPower);
//    }
//
//    public void followAprilTag() {
//        this.mode = Mode.FOLLOW_APRIL_TAG;
//    }
//
//    private MoveData calculatePower() {
//        switch (mode) {
//            case DRIVE_STRAIGHT: return driveStraight();
//            case TURN: return turn();
//            case FOLLOW_APRIL_TAG: return followAprilTagTick();
//            default: return new MoveData();
//        }
//    }
//
//    private MoveData followAprilTagTick() {
//        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
//        for (AprilTagDetection detection : currentDetections) {
//            double  rangeError      = (detection.ftcPose.range - DESIRED_DISTANCE);
//            double  headingError    = detection.ftcPose.bearing - 0;
//            double  yawError        = detection.ftcPose.yaw - 0;
//            MoveData movement = MoveData.straight((float) rangeError, 0f, 1f);
//
////            // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
////            double  rangeError      = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
////            double  headingError    = desiredTag.ftcPose.bearing;
////            double  yawError        = desiredTag.ftcPose.yaw;
////
////            // Use the speed and turn "gains" to calculate how we want the robot to straight.
////            drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
////            turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
////            strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
//
//            return movement;
//        }
//
//        return new MoveData();
//    }
//
//    public boolean inProgress() {
//        switch (mode) {
//            case DRIVE_STRAIGHT: return driveStraightInProgress();
//            case TURN: return turnInProgress();
//            default: return false;
//        }
//    }
//
//    public boolean done() {
//        return !inProgress();
//    }
//
//    public void moveStraight(int distance) {
//        this.mode = Mode.DRIVE_STRAIGHT;
//        this.targetPosition = position() + distance;
//        this.targetHeading = yaw();
//    }
//
//    public void turn(double turnAmount) {
//        this.mode = Mode.TURN;
//        double targetAngle = this.targetHeading + turnAmount;
//
//        if (targetAngle > 180) {
//            this.targetHeading = targetAngle - 360;
//        } else if (targetAngle < -180) {
//            this.targetHeading = targetAngle + 360;
//        } else {
//            this.targetHeading = targetAngle;
//        }
//    }
//
//    private boolean driveStraightInProgress() {
//        boolean done = closeEnough(position(), this.targetPosition, 8);
//        if (done) { this.mode = Mode.NONE; }
//        return !done;
//    }
//
//    private boolean turnInProgress() {
//        boolean done = closeEnough(yaw(), this.targetHeading, 1);
//        if (done) { this.mode = Mode.NONE; }
//        return !done;
//    }
//
//    private boolean closeEnough(double a, double b, int threshold) {
//        return Util.closeEnough(a,b,threshold);
//    }
//
//    private MoveData driveStraight() {
//        MoveData turnMovement = turnPower(5d);
//        MoveData moveMovement = movePower();
//
//        return turnMovement.add(moveMovement);
//    }
//
//    private MoveData turn() {
//        return turnPower(1d);
//    }
//
//    private int position() {
//        return leftFront.getCurrentPosition();
//    }
//
//    private MoveData turnPower(double gain) {
//        double distanceToTurn = this.targetHeading - yaw();
//        double shortDistanceToTurn;
//
//        if (distanceToTurn > 180) {
//            shortDistanceToTurn = distanceToTurn - 360;
//        } else if (distanceToTurn < -180) {
//            shortDistanceToTurn = distanceToTurn + 360;
//        } else {
//            shortDistanceToTurn = distanceToTurn;
//        }
//
//        float power = (float) (shortDistanceToTurn * gain / 90d);
//
//        return MoveData.turn(power, 0.05f, 0.6f);
//    }
//
//    private double yaw() {
//        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//    }
//
//    private MoveData movePower() {
//        int distanceToMove = this.targetPosition - position();
//        float power = (float) distanceToMove / 200f;
//
//        return MoveData.straight(power, 0.05f, 0.6f);
//    }
//}
