package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutoMoveController implements Tickable {
    enum Mode {
        DRIVE_STRAIGHT, TURN
    }
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;
    private final IMU imu;
    private final Telemetry telemetry;
    private int targetPosition;
    private double targetHeading;
    private Mode mode;
    private boolean inProgress;

    public AutoMoveController(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, IMU imu, Telemetry telemetry) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;
        this.imu = imu;
        this.telemetry = telemetry;
    }

    public void tick() {
        Output.Movement movement = calculatePower();
        leftFront.setPower(movement.frontLeftPower);
        rightFront.setPower(movement.frontRightPower);
        leftBack.setPower(movement.rearLeftPower);
        rightBack.setPower(movement.rearRightPower);
        telemetry.addData("targetPosition", this.targetPosition);
        telemetry.addData("wheelPosition", position());
        telemetry.addData("targetHeading", this.targetHeading);
        telemetry.addData("yaw", yaw());
        telemetry.addData("frontLeftPower", movement.frontLeftPower);
        telemetry.addData("frontRightPower", movement.frontRightPower);
        telemetry.addData("rearLeftPower", movement.rearLeftPower);
        telemetry.addData("rearRightPower", movement.rearRightPower);
    }

    private Output.Movement calculatePower() {
        switch (mode) {
            case DRIVE_STRAIGHT: return driveStraight();
            case TURN: return turn();
            default: return new Output.Movement();
        }
    }

    public boolean inProgress() {
        switch (mode) {
            case DRIVE_STRAIGHT: driveStraightInProgress();
            case TURN: return turnInProgress();
            default: return false;
        }
    }

    public void moveStraight(int distance) {
        this.mode = Mode.DRIVE_STRAIGHT;
        this.targetPosition = position() + distance;
        this.targetHeading = yaw();
    }

    public void turn(double turnAmount) {
        this.mode = Mode.TURN;
        double targetAngle = this.targetHeading + turnAmount;

        if (targetAngle > 180) {
            this.targetHeading = targetAngle - 360;
        } else if (targetAngle < -180) {
            this.targetHeading = targetAngle + 360;
        } else {
            this.targetHeading = targetAngle;
        }
    }

    private boolean driveStraightInProgress() {
        boolean done = closeEnough(position(), this.targetPosition, 8);
        if (done) { this.mode = null; }
        return !done;
    }

    public boolean turnInProgress() {
        boolean done = closeEnough(yaw(), this.targetHeading, 1);
        if (done) { this.mode = null; }
        return !done;
    }

    private boolean closeEnough(double a, double b, int threshold) {
        double difference = Math.abs(a - b);
        return difference <= threshold;
    }

    private Output.Movement driveStraight() {
        Output.Movement turnMovement = turnPower(5d);
        Output.Movement moveMovement = movePower();

        return turnMovement.add(0f, 1f, moveMovement);
    }

    private Output.Movement turn() {
        return turnPower(1d);
    }

    private int position() {
        return leftFront.getCurrentPosition();
    }

    private Output.Movement turnPower(double gain) {
        double distanceToTurn = this.targetHeading - yaw();
        double shortDistanceToTurn;

        if (distanceToTurn > 180) {
            shortDistanceToTurn = distanceToTurn - 360;
        } else if (distanceToTurn < -180) {
            shortDistanceToTurn = distanceToTurn + 360;
        } else {
            shortDistanceToTurn = distanceToTurn;
        }

        float power = (float) (shortDistanceToTurn * gain / 90d);

        return Output.Movement.turn(power, 0.05f, 0.6f);
    }

    private double yaw() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    private Output.Movement movePower() {
        int distanceToMove = this.targetPosition - position();
        float power = (float) distanceToMove / 200f;

        return Output.Movement.move(power, 0.05f, 0.6f);
    }
}
