package ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_pre_run;

import ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.DiscreteParameterGenerator;
import ecnu.dll.utils.CatchSignal;

public class GenerateDiscreteParametersForTrajectory {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        DiscreteParameterGenerator.generateParametersForTrajectory();
    }
}
