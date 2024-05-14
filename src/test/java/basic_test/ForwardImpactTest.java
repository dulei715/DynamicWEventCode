package basic_test;

import cn.edu.dll.basic.BasicCalculation;
import ecnu.dll.struts.direct_stream.ForwardImpactStream;
import org.junit.Test;

import java.util.Random;

public class ForwardImpactTest {
    @Test
    public void fun1() {
        ForwardImpactStream forwardImpactStream = new ForwardImpactStream();
        Random random = new Random(1);
        Double tempDouble;
        for (int i = 0; i < 10; i++) {
            tempDouble = random.nextDouble();
            tempDouble = BasicCalculation.getPrecisionValue(tempDouble, 2);
            forwardImpactStream.addElement(tempDouble, random.nextInt(5) + 1);
            forwardImpactStream.showStream();
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
}
