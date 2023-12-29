package org.firstinspires.ftc.teamcode.state.utils;

import org.firstinspires.ftc.teamcode.Output;

public class Decision implements PlanPart {
    private final Callable<PlanPart> planPartPicker;
    private PlanPart chosenPlanPart;

    public Decision(Callable<PlanPart> planPartPicker) {
        this.planPartPicker = planPartPicker;
    }

    public boolean done() {
        return getChosenPlanPart().done();
    }

    public Output.Movement movement() {
        return getChosenPlanPart().movement();
    }

    private PlanPart getChosenPlanPart() {
        if (chosenPlanPart == null) { this.chosenPlanPart = planPartPicker.call(); }
        return this.chosenPlanPart;
    }
}
