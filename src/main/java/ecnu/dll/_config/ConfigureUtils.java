package ecnu.dll._config;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.configure.XMLConfigure;
import cn.edu.dll.constant_values.ConstantValues;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.List;

public class ConfigureUtils {

//    static {
//        System.out.println(projectPath);
//        String path = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "development", "config", "parameter_config.xml");
//        System.out.println(path);
//    }

    public static Double getPrivacyBudgetUpperBound() {
        Document document = Constant.xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementPrivacyBudget = (Element)candidateSet.selectNodes("./attribute[@name='PrivacyBudgetUpperBound']").get(0);
        String budgetUpperBoundString = elementPrivacyBudget.element("value").getTextTrim();
        Double budgetUpperBound = Double.valueOf(budgetUpperBoundString);
        return budgetUpperBound;
    }
    public static Integer getWindowSizeLowerBound() {
        Document document = Constant.xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementWindowSize = (Element) candidateSet.selectNodes("./attribute[@name='WindowSizeLowerBound']").get(0);
        String windowSizeLowerBoundString = elementWindowSize.element("value").getTextTrim();
        Integer windowSizeLowerBound = Integer.valueOf(windowSizeLowerBoundString);
        return windowSizeLowerBound;
    }

    public static Double getBackwardPrivacyBudgetUpperBoundDifference() {
        Document document = Constant.xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementPrivacyBudget = (Element)candidateSet.selectNodes("./attribute[@name='BackwardPrivacyBudgetUpperBoundDifference']").get(0);
        String budgetUpperBoundString = elementPrivacyBudget.element("value").getTextTrim();
        Double budgetUpperBound = Double.valueOf(budgetUpperBoundString);
        return budgetUpperBound;
    }

    public static Double getBackwardPrivacyBudgetLowerBoundDifference() {
        Document document = Constant.xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementPrivacyBudget = (Element)candidateSet.selectNodes("./attribute[@name='BackwardPrivacyBudgetLowerBoundDifference']").get(0);
        String budgetLowerBoundString = elementPrivacyBudget.element("value").getTextTrim();
        Double budgetLowerBound = Double.valueOf(budgetLowerBoundString);
        return budgetLowerBound;
    }

    public static Integer getDefaultTypeSize() {
        Integer result;
        try {
            result = (Integer) Constant.xmlConfigure.getIndependentData("UserType", "default", "default").getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String getDatasetBasicPath() {
        Document document = Constant.xmlConfigure.getDocument();
        Element relativePathElement = (Element) document.selectNodes("//datasets/basicPath[@type='relative']").get(0);
        String relativePath = relativePathElement.getTextTrim(), absolutePath;
        relativePath = relativePath.replace(";", ConstantValues.FILE_SPLIT);
        absolutePath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.projectPath, relativePath);
        File datasetFile = new File(absolutePath);
        if (datasetFile.exists()) {
            return datasetFile.getAbsolutePath();
        }
        List<Element> elemnetList = document.selectNodes("//datasets/basicPath[@type='absolute']");
        for (Element element : elemnetList) {
            absolutePath = element.getTextTrim();
            absolutePath = absolutePath.replace(";", ConstantValues.FILE_SPLIT);
            datasetFile = new File(absolutePath);
            if (datasetFile.exists()) {
                return datasetFile.getAbsolutePath();
            }
        }
        throw new RuntimeException("No valid data set path!");
    }

    public static String getDatasetFileName(String tagName) {
        Document document = Constant.xmlConfigure.getDocument();
        Element element = (Element)document.selectNodes("//datasets/fileName[@name='" + tagName + "']").get(0);
        return element.getTextTrim();
    }



    public static void main0(String[] args) {
        Double privacyBudgetUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        System.out.println(privacyBudgetUpperBound);
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        System.out.println(windowSizeLowerBound);
        Double backUpperBound = ConfigureUtils.getBackwardPrivacyBudgetUpperBoundDifference();
        System.out.println(backUpperBound);
        Double backLowerBound = ConfigureUtils.getBackwardPrivacyBudgetLowerBoundDifference();
        System.out.println(backLowerBound);
    }

    public static void main(String[] args) {
//        String datasetBasicPath = ConfigureUtils.getDatasetBasicPath();
//        System.out.println(datasetBasicPath);
//        String fileName = ConfigureUtils.getDatasetFileName("trajectories");
//        String fileName = ConfigureUtils.getDatasetFileName("checkIn");
//        System.out.println(fileName);
        System.out.println(Constant.projectPath);
    }
}
