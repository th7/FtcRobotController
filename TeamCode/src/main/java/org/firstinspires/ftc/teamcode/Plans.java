package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.state.utils.Plan;
import org.firstinspires.ftc.teamcode.state.utils.Step;
import org.firstinspires.ftc.teamcode.state.utils.PlanPart;

public class Plans {
    private final Compute compute;

    Plans(Compute compute) {
        this.compute = compute;
    }

    public Plan teamProp() {
        return new Plan(
                closeClaws(),
                moveToSpikeMarks(),
                openBottomClaw(),
                moveBackFromSpikeMarks()
        );
    }

    public Plan backStageRed() {
        return backstage(compute.turnRight);
    }

    public Plan backStageBlue() {
        return backstage(compute.turnLeft);
    }

    public Plan frontStageRed() {
        return frontStage(1);
    }

    public Plan frontStageBlue() {
        return frontStage(-1);
    }

    private Plan backstage(double turn) {
        return new Plan(
                teamProp(),

                turn(turn),
                moveBackStageShort(),
                openTopClaw(),
                nudgeBack()
        );
    }

    private Plan frontStage(int turnDirection) {
        return new Plan(
                teamProp(),

                turn(-90 * turnDirection),
                moveToWing(),
                turn(90 * turnDirection),
                wingToMiddle(),
                turn(45 * turnDirection),
                moveToMiddle(),
                turn(45 * turnDirection),
                moveBackStageLong(),
                openTopClaw(),
                nudgeBack()
        );
    }

    private Step closeClaws() {
        return new Step(
                compute::closeClaws,
                compute::clawComplete
        );
    }

    private Step moveToSpikeMarks() {
        return moveTiles(1.3);
    }

    private Step openBottomClaw() {
        return new Step(
                compute::openBottomClaw,
                compute::clawComplete
        );
    }

    private Step moveBackFromSpikeMarks() {
        return moveTiles(0.9);
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
                compute::openTopClaw,
                compute::clawComplete
        );
    }

    private PlanPart nudgeBack() {
        return moveTiles(-0.2);
    }

    private Step move(double distance) {
        return new Step(
                () -> compute.move(distance),
                compute::driveMovement,
                compute::moveComplete
        );
    }

    private Step moveTiles(double tiles) {
        return move(compute.oneTile * tiles);
    }

    private Step turn(double angle) {
        return new Step(
                () -> compute.turn(angle),
                () -> compute.autoTurn(1d),
                compute::turnComplete
        );
    }
}
