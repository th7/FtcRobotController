package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.state.utils.LinearPlan;
import org.firstinspires.ftc.teamcode.state.utils.Step;
import org.firstinspires.ftc.teamcode.state.utils.PlanPart;

public class Plans {
    public static final double oneTile = 1275d;

    public static PlanPart teamProp() {
        return new LinearPlan(
//                closeClaws(),
                moveToSpikeMarks(),
//                openBottomClaw(),
                moveBackFromSpikeMarks()
        );
    }

    public static PlanPart autoOpLowBasket() {
        return new LinearPlan(
                armToLowBasket(),
                moveTiles(-0.3),
                openClaws()
                );
    }

    public static PlanPart autoOpLowBasketAndPark() {
        return new LinearPlan(
                armToLowBasket(),
                moveTiles(-0.3),
                openClaws(),
                closeClaws(),
                armToAboveFloor(),
                moveTiles(3.3)
        );
    }


    public static PlanPart autoOpDrive() {
        return new LinearPlan(
                armToAboveFloor(),
                moveTiles(2.2)
        );
    }

    public static PlanPart detectAprilTag() {
        return new LinearPlan(
                new Step(
                        Move::detectAprilTag,
                        () -> false
                )
        );
    }

    public static PlanPart autoOpDropOffAndDrive() {
        return new LinearPlan(
                armToAboveFloor(),
                moveTiles(1.2),
                moveTiles(-4.2)
        );

    }
    public static PlanPart backStageRed() {
        return backstage(1);
    }

    public static PlanPart backStageBlue() {
        return backstage(-1);
    }

    public static PlanPart frontStageRed() {
        return frontStage(1);
    }

    public static PlanPart frontStageBlue() {
        return frontStage(-1);
    }

    private static PlanPart backstage(int turnDirection) {
        return new LinearPlan(
                teamProp(),

                turn(90d * turnDirection),
                moveBackStageShort(),
//                openTopClaw(),
                nudgeBack()
        );
    }

    private static PlanPart frontStage(int turnDirection) {
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
//                openTopClaw(),
                nudgeBack()
        );
    }

//    private static PlanPart closeClaws() {
//        return new Step(
//                clawController::closeClaws,
//                clawController::done
//        );
//    }

    private static PlanPart moveToSpikeMarks() {
        return moveTiles(1.3);
    }

//    private static PlanPart openBottomClaw() {
//        return new Step(
//                clawController::openBottomClaw,
//                clawController::done
//        );
//    }

    private static PlanPart moveBackFromSpikeMarks() {
        return moveTiles(-0.9);
    }

    private static PlanPart moveBackStageShort() {
        return moveTiles(1.8);
    }

    private static PlanPart moveToWing() {
        return moveTiles(0.8);
    }

    private static PlanPart wingToMiddle() {
        return moveTiles(1.7);
    }

    private static PlanPart moveToMiddle() {
        return moveTiles(0.8);
    }

    private static PlanPart moveBackStageLong() {
        return moveTiles(4);
    }

//    private static PlanPart openTopClaw() {
//        return new Step(
//                clawController::openTopClaw,
//                clawController::done
//        );
//    }

    private static PlanPart nudgeBack() {
        return moveTiles(-0.2);
    }

    private static PlanPart move(double distance) {
        return new Step(
                "move " + String.valueOf(distance),
                () -> Move.moveStraight((int) distance),
                Move::done
        );
    }

    private static PlanPart moveTiles(double tiles) {
        return move(oneTile * tiles);
    }

    private static PlanPart turn(double angle) {
        return new Step(
                "turn " + String.valueOf(angle),
                () -> Move.turn(angle),
                Move::done
        );
    }

    private static PlanPart armToAboveFloor() {
        return new Step(
                "armToAboveFloor",
                () -> Arm.armToAboveFloor(),
                Arm::done
        );
    }

    private static PlanPart armToLowBasket() {
        return new Step(
                "armToLowBasket",
                () -> Arm.armToLowBasket(),
                Arm::done
        );
    }

    private static PlanPart openClaws() {
        return new Step(
                "openClaws",
                () -> Arm.openClaw(),
                Arm::done
        );
    }

    private static PlanPart closeClaws() {
        return new Step(
                "closeClaws",
                () -> Arm.closeClaw(),
                Arm::done
        );
    }

    public static PlanPart aprilTag() {
        return new Step(
                Move::followAprilTag,
                () -> false
        );
    }
}
