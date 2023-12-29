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
                closeClaws(),
                moveToSpikeMarks(),
                openBottomClaw(),
                moveBackFromSpikeMarks()
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

    private LinearStateMachine backstage(double turn) {
        return new LinearStateMachine(
                teamProp(),

                turn(turn),
                moveBackStageShort(),
                openTopClaw(),
                nudgeBack()
        );
    }

    private LinearStateMachine frontStage(int turnDirection) {
        return new LinearStateMachine(
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

    private State closeClaws() {
        return new State(
                compute::closeClaws,
                compute::clawComplete
        );
    }

    private State moveToSpikeMarks() {
        return moveTiles(1.3);
    }

    private State openBottomClaw() {
        return new State(
                compute::openBottomClaw,
                compute::clawComplete
        );
    }

    private State moveBackFromSpikeMarks() {
        return moveTiles(0.9);
    }

    private Stateful moveBackStageShort() {
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

    private Stateful openTopClaw() {
        return new State(
                compute::openTopClaw,
                compute::clawComplete
        );
    }

    private Stateful nudgeBack() {
        return moveTiles(-0.2);
    }

    private State move(double distance) {
        return new State(
                () -> compute.move(distance),
                compute::driveMovement,
                compute::moveComplete
        );
    }

    private State moveTiles(double tiles) {
        return move(compute.oneTile * tiles);
    }

    private State turn(double angle) {
        return new State(
                () -> compute.turn(angle),
                () -> compute.autoTurn(1d),
                compute::turnComplete
        );
    }
}
