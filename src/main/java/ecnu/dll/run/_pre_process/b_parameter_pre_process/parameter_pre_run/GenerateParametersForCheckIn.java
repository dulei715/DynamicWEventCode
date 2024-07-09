package ecnu.dll.run._pre_process.b_parameter_pre_process.parameter_pre_run;

import ecnu.dll.utils.CatchSignal;

import static ecnu.dll.run._pre_process.b_parameter_pre_process.parameter_generator.ParameterGenerator.generateParametersForCheckIn;

public class GenerateParametersForCheckIn {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        generateParametersForCheckIn();
    }
}
