package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.state.utils.Stateful;

public class BaseAutoOp2023 extends BaseOp2023 {
    public Stateful stateMachine;
    public Plan plan = new Plan(compute);

    @Override
    public void init() {
        super.init();
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public Output compute() {
        Output output = new Output();

        if (stateMachine.done()) {
            output.addTel("done", true);
            return output;
        }

        output.topClawPosition = memory.topClawPosition;
        output.bottomClawPosition = memory.bottomClawPosition;
        output.armMotorPower = compute.autoArmPower();
        output.movement = stateMachine.movement();

        output.addTel("targetMovePosition", memory.targetMovePosition);
        output.addTel("wheelPosition", compute.input.wheelPosition);

        output.addTel("targetAngle", memory.targetAngle);
        output.addTel("yaw", compute.input.yaw);

        output.addTel("frontLeftPower", output.movement.frontLeftPower);
        output.addTel("frontRightPower", output.movement.frontRightPower);
        output.addTel("rearLeftPower", output.movement.rearLeftPower);
        output.addTel("rearRightPower", output.movement.rearRightPower);

        return output;
    }
}
