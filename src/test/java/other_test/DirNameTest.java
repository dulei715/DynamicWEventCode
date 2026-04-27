package other_test;

import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll.utils.run.ParameterUtils;
import org.junit.Test;

public class DirNameTest {
    @Test
    public void funA() {
//        String dirName = "u_0-1_w_120";
        String dirName = "u_0-5_p_0-6";
        PureTriple<Double, Integer, Integer> result = ParameterUtils.extractUserRatioAndOtherParametersAccordingFileDirName(dirName);
        System.out.println(result);
    }
}
