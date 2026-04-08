package ecnu.dll.utils.run;

import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.pair.PureTriple;

public class ParameterUtils {
    public static BasicPair<Double, Integer> extractBudgetWindowSizeParametersAccordingFileDirName(String dirName) {
        String[] paramStringArray = dirName.split("_");
        Double privacyBudget = Double.valueOf(paramStringArray[1].replace("-", "."));
        Integer windowSize = Integer.valueOf(paramStringArray[3]);
        BasicPair<Double, Integer> result = new BasicPair<>(privacyBudget, windowSize);
        return result;
    }

    public static PureTriple<Double, Integer, Integer> extractBudgetWindowSizeDimensionParametersAccordingFileDirName(String dirName) {
        String[] paramStringArray = dirName.split("_");
        Double privacyBudget = Double.valueOf(paramStringArray[1].replace("-", "."));
        Integer windowSize = Integer.valueOf(paramStringArray[3]);
        Integer dimension = null;
        if (paramStringArray.length > 4) {
            dimension = Integer.valueOf(paramStringArray[5]);
        }
        return new PureTriple<>(privacyBudget, windowSize, dimension);
    }
}
