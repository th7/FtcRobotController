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

    public static PlanPart autoOpDoubleLowBasket() {
        return new LinearPlan(
                armToLowBasketNoWait(),
                moveTiles(-0.3),
                waitForArm(),
                closeClaws(), // its already closed but we just want it to wait so that the extra arm movement doesn't fling it off the field
                openClaws(),
                closeClawsNoWait(),
                armToFloorForwardsNoWait(),
                strafe(2),
                turn(-86),
                armToFloorBackwardNoWait(),
                openClawsNoWait(),
                moveTiles(-0.25),
                waitForArm(),
                moveTiles(-0.3),
                closeClaws(),
                armToLowBasketNoWait(),
                turn(135),
                moveTiles(-1),
                waitForArm(),
                openClaws(),
                closeClaws(),
                openClaws(),
                closeClaws(),
                openClaws(),
                closeClaws(),
                openClaws(),
                closeClaws()
        );
    }

    public static PlanPart autoOpDoubleLowBasketAndPark() {
        return new LinearPlan(
                armToLowBasket(),
                moveTiles(-0.3),
                openClaws(),
                armToAboveFloor(),
                turn(90),
                armToFloor(),
                closeClaws(),
                turn(-90),
                armToLowBasket(),
                openClaws(),
                closeClaws(),
                armToAboveFloor(),
                moveTiles(3.3)
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

    public static PlanPart orbitAprilTag() {
        return new LinearPlan(
                new Step(
                        Move::orbitAprilTag,
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

    private static PlanPart strafe(double seconds) {
        return new Step(
                "strafe",
                () -> Move.strafe(seconds),
                Move::done
        );
    }

    private static PlanPart turn(double angle) {
        return new Step(
                "turn " + String.valueOf(angle),
                () -> Move.turn(angle),
                //does need lambda because it has an argument
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

    private static PlanPart armToAboveFloorNoWait() {
        return new Step(
                "armToAboveFloorNoWait",
                () -> Arm.armToAboveFloor(),
                () -> true
        );
    }

    private static PlanPart armToFloorBackwardNoWait() {
        return new Step(
                "armToFloorBackwardNoWait",
                () -> Arm.armToFloorBackward(),
                () -> true
        );
    }

    private static PlanPart armToFloor() {
        return new Step(
                "armToFloor",
                () -> Arm.armToFloor(),
                //does not need lambda :)
                Arm::done
                //Arm.armToFloor lambda thingy could look like the done
        );
    }

    private static PlanPart armToFloorForwardsNoWait() {
        return new Step(
                "armToFloor",
                () -> Arm.armToFloorForwards(),
                //does not need lambda :)
                () -> true
                //Arm.armToFloor lambda thingy could look like the done
        );
    }

    private static PlanPart armToLowBasket() {
        return new Step(
                "armToLowBasket",
                () -> Arm.armToLowBasket(),
                Arm::done
        );
    }

    private static PlanPart armToLowBasketNoWait() {
        return new Step(
                "armToLowBasket",
                () -> Arm.armToLowBasket(),
                () -> true //replaces usual done method
                //has to be a lambda because it needs to be called
        );
    }

//    private static PlanPart goToFirstSample() {
//        return new Step(
//                "goToFirstSample",
//                () ->
//        );
//    }

    private static PlanPart waitForArm() {
        return new Step(
          "waitForArm",
                () -> {},
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

    private static PlanPart openClawsNoWait() {
        return new Step(
                "openClaws",
                () -> Arm.openClaw(),
                () -> true
        );
    }

    private static PlanPart closeClaws() {
        return new Step(
                "closeClaws",
                () -> Arm.closeClaw(),
                Arm::done
        );
    }

    private static PlanPart closeClawsNoWait() {
        return new Step(
                "closeClaws",
                () -> Arm.closeClaw(),
                () -> true
        );
    }
    public static PlanPart aprilTag() {
        return new Step(
                Move::followAprilTag,
                () -> false
        );
    }
}
