package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.state.utils.LinearPlan;
import org.firstinspires.ftc.teamcode.state.utils.Step;
import org.firstinspires.ftc.teamcode.state.utils.PlanPart;

public class Plans {
    private final AutoArmController armController;
    private final AutoMoveController moveController;
    private final AutoClawController clawController;

    public static final double oneTile = 1275d;

    Plans(AutoArmController armController, AutoMoveController moveController, AutoClawController clawController) {
        this.armController = armController;
        this.moveController = moveController;
        this.clawController = clawController;
    }

    public void tick() {
//        armController.tick();
        moveController.tick();
//        clawController.tick();
    }

    public PlanPart teamProp() {
        return new LinearPlan(
                closeClaws(),
                moveToSpikeMarks(),
                openBottomClaw(),
                moveBackFromSpikeMarks()
        );
    }
    public PlanPart autoOpDrive() {
        return moveTiles(2.2);
    }

    public PlanPart autoOpDropOffAndDrive() {
        return new LinearPlan(
                moveTiles(1.2),
                moveTiles(-4.2)
        );

    }
    public PlanPart backStageRed() {
        return backstage(1);
    }

    public PlanPart backStageBlue() {
        return backstage(-1);
    }

    public PlanPart frontStageRed() {
        return frontStage(1);
    }

    public PlanPart frontStageBlue() {
        return frontStage(-1);
    }

    private PlanPart backstage(int turnDirection) {
        return new LinearPlan(
                teamProp(),

                turn(90d * turnDirection),
                moveBackStageShort(),
                openTopClaw(),
                nudgeBack()
        );
    }

    private PlanPart frontStage(int turnDirection) {
        return new LinearPlan(
                teamProp(),

                turn(-90d * turnDirection),
                moveToWing(),
                turn(90d * turnDirection),
                wingToMiddle(),
                turn(45d * turnDirection),
                moveToMiddle(),
                turn(45d * turnDirection),
                moveBackStageLong(),
                openTopClaw(),
                nudgeBack()
        );
    }

    private PlanPart closeClaws() {
        return new Step(
                clawController::closeClaws,
                clawController::done
        );
    }

    private PlanPart moveToSpikeMarks() {
        return moveTiles(1.3);
    }

    private PlanPart openBottomClaw() {
        return new Step(
                clawController::openBottomClaw,
                clawController::done
        );
    }

    private PlanPart moveBackFromSpikeMarks() {
        return moveTiles(-0.9);
    }

    private PlanPart moveBackStageShort() {
        return moveTiles(1.8);
    }

    private PlanPart moveToWing() {
        return moveTiles(0.8);
    }

    private PlanPart wingToMiddle() {
        return moveTiles(1.7);
    }

    private PlanPart moveToMiddle() {
        return moveTiles(0.8);
    }

    private PlanPart moveBackStageLong() {
        return moveTiles(4);
    }

    private PlanPart openTopClaw() {
        return new Step(
                clawController::openTopClaw,
                clawController::done
        );
    }

    private PlanPart nudgeBack() {
        return moveTiles(-0.2);
    }

    private PlanPart move(double distance) {
        return new Step(
                () -> moveController.moveStraight((int) distance),
                moveController::done
        );
    }

    private PlanPart moveTiles(double tiles) {
        return move(oneTile * tiles);
    }

    private PlanPart turn(double angle) {
        return new Step(
                () -> moveController.turn(angle),
                moveController::done
        );
    }

    public PlanPart aprilTag() {
        return new Step(
                moveController::followAprilTag,
                () -> false
        );
    }
}
