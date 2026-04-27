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

    public static PureTriple<Double, Integer, Integer> extractUserRatioAndOtherParametersAccordingFileDirName(String dirName) {
        String[] paramStringArray = dirName.split("_");
        Double userRatio = Double.valueOf(paramStringArray[1].replace("-", "."));
        Integer secondParam = null;
        Integer dimension = null;

        int dIndex = -1;
        for (int i = 0; i < paramStringArray.length; i++) {
            if ("d".equals(paramStringArray[i])) {
                dIndex = i;
                break;
            }
        }

        if (dIndex != -1 && dIndex + 1 < paramStringArray.length) {
            dimension = Integer.valueOf(paramStringArray[dIndex + 1]);
        }

        if (dirName.contains("_w_")) {
            int wIndex = -1;
            for (int i = 0; i < paramStringArray.length; i++) {
                if ("w".equals(paramStringArray[i])) {
                    wIndex = i;
                    break;
                }
            }
            if (wIndex != -1 && wIndex + 1 < paramStringArray.length) {
                secondParam = Integer.valueOf(paramStringArray[wIndex + 1]);
            }
        } else if (dirName.contains("_p_")) {
            int pIndex = -1;
            for (int i = 0; i < paramStringArray.length; i++) {
                if ("p".equals(paramStringArray[i])) {
                    pIndex = i;
                    break;
                }
            }
            if (pIndex != -1 && pIndex + 1 < paramStringArray.length) {
                String pValue = paramStringArray[pIndex + 1];
                if (pValue.contains("-")) {
                    secondParam = (int)(Double.parseDouble(pValue.replace("-", ".")) * 10);
                } else {
                    secondParam = Integer.valueOf(pValue);
                }
            }
        }

        return new PureTriple<>(userRatio, secondParam, dimension);
    }

}
