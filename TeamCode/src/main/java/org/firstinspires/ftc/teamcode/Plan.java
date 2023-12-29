package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.state.utils.LinearStateMachine;
import org.firstinspires.ftc.teamcode.state.utils.State;
import org.firstinspires.ftc.teamcode.state.utils.Stateful;

public class Plan {
    private final Compute compute;

    Plan(Compute compute) {
        this.compute = compute;
    }

    public LinearStateMachine teamProp() {
        return new LinearStateMachine(
                closeClawsState(),
                moveToSpikeMarks(),
                openBottomClawState(),
                moveBackFromSpikeMarks()
        );
    }

    public LinearStateMachine backstage(double turn) {
        return new LinearStateMachine(
                teamProp(),

                turnState(turn),
                toBackStageShort(),
                openTopClawState(),
                nudgeBack()
        );
    }

    public LinearStateMachine frontStage(int turnDirection) {
        return new LinearStateMachine(
                teamProp(),

                turnState(-90 * turnDirection),
                moveToWing(),
                turnState(90 * turnDirection),
                wingToMiddle(),
                turnState(45 * turnDirection),
                moveToMiddle(),
                turnState(45 * turnDirection),
                moveBackStageLong(),
                openTopClawState(),
                nudgeBack()
        );
    }

    public LinearStateMachine backStageRed() {
        return backstage(compute.turnRight);
    }

    public LinearStateMachine backStageBlue() {
        return backstage(compute.turnLeft);
    }

    public LinearStateMachine frontStageRed() {
        return frontStage(1);
    }

    public LinearStateMachine frontStageBlue() {
        return frontStage(-1);
    }

    private State closeClawsState() {
        return new State(
                () -> { compute.closeClaws(); compute.waitFor(0.6); },
                compute::waitComplete//input.elapsedSeconds > memory.targetWaitSeconds
        );
    }

    private State moveToSpikeMarks() {
        return moveTiles(1.3);
    }

    private State openBottomClawState() {
        return new State(
                () -> { compute.openBottomClaw(); compute.waitFor(0.6); },
                compute::waitComplete
        );
    }

    private State moveBackFromSpikeMarks() {
        return moveTiles(0.9);
    }

    private Stateful toBackStageShort() {
        return moveTiles(1.8);
    }

    private Stateful moveToWing() {
        return moveTiles(0.8);
    }

    private Stateful wingToMiddle() {
        return moveTiles(1.7);
    }

    private Stateful moveToMiddle() {
        return moveTiles(0.8);
    }

    private Stateful moveBackStageLong() {
        return moveTiles(4);
    }

    private Stateful openTopClawState() {
        return new State(
                () -> { compute.openTopClaw(); compute.waitFor(0.6); },
                compute::waitComplete
        );
    }

    private Stateful nudgeBack() {
        return moveTiles(-0.2);
    }

    private State moveState(double distance) {
        return new State(
                () -> compute.move(distance),
                compute::driveMovement,
                compute::moveCloseEnough
        );
    }

    private State moveTiles(double tiles) {
        return moveState(compute.oneTile * tiles);
    }

    private State turnState(double angle) {
        return new State(
                () -> compute.turn(angle),
                () -> compute.autoTurn(1d),
                compute::turnCloseEnough
        );
    }
}
