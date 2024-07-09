package ecnu.dll.run._pre_process.b_parameter_pre_process.parameter_pre_run;

import ecnu.dll.run._pre_process.b_parameter_pre_process.parameter_generator.ParameterGenerator;
import ecnu.dll.utils.CatchSignal;

public class GenerateParametersForTrajectory {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        ParameterGenerator.generateParametersForTrajectory();
    }
}
