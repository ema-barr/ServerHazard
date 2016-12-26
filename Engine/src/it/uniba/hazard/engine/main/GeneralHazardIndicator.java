package it.uniba.hazard.engine.main;

import java.util.List;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class GeneralHazardIndicator {
    private List<Integer> steps;
    private int currentStep;

    public GeneralHazardIndicator(List<Integer> steps) {
        this.steps = steps;
    }

    public void raiseHazardLevel() {
        if (currentStep < steps.size()) {
            currentStep++;
        }
    }

    public int getCurrentLevel() {
        return steps.get(currentStep);
    }
}
