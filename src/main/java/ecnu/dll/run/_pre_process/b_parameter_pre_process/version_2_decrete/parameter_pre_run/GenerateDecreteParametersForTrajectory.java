package ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_pre_run;

import ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.DecreteParameterGenerator;
import ecnu.dll.utils.CatchSignal;

public class GenerateDecreteParametersForTrajectory {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        DecreteParameterGenerator.generateParametersForTrajectory();
    }
}
