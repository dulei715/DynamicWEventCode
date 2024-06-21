package ecnu.dll._config;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.configure.XMLConfigure;
import cn.edu.dll.constant_values.ConstantValues;
import org.dom4j.Document;
import org.dom4j.Element;

public class ConfigureUtils {
    public static String projectPath = System.getProperty("user.dir");
    private static XMLConfigure xmlConfigure = new XMLConfigure(StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "development", "config", "parameter_config.xml"));
    public static Double getPrivacyBudgetUpperBound() {
        Document document = xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementPrivacyBudget = (Element)candidateSet.selectNodes("./attribute[@name='PrivacyBudgetUpperBound']").get(0);
        String budgetUpperBoundString = elementPrivacyBudget.element("value").getTextTrim();
        Double budgetUpperBound = Double.valueOf(budgetUpperBoundString);
        return budgetUpperBound;
    }
    public static Integer getWindowSizeLowerBound() {
        Document document = xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementWindowSize = (Element) candidateSet.selectNodes("./attribute[@name='WindowSizeLowerBound']").get(0);
        String windowSizeLowerBoundString = elementWindowSize.element("value").getTextTrim();
        Integer windowSizeLowerBound = Integer.valueOf(windowSizeLowerBoundString);
        return windowSizeLowerBound;
    }

    public static Double getBackwardPrivacyBudgetUpperBoundDifference() {
        Document document = xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementPrivacyBudget = (Element)candidateSet.selectNodes("./attribute[@name='BackwardPrivacyBudgetUpperBoundDifference']").get(0);
        String budgetUpperBoundString = elementPrivacyBudget.element("value").getTextTrim();
        Double budgetUpperBound = Double.valueOf(budgetUpperBoundString);
        return budgetUpperBound;
    }

    public static Double getBackwardPrivacyBudgetLowerBoundDifference() {
        Document document = xmlConfigure.getDocument();
        Element candidateSet = document.getRootElement().element("candidateSet");
        Element elementPrivacyBudget = (Element)candidateSet.selectNodes("./attribute[@name='BackwardPrivacyBudgetLowerBoundDifference']").get(0);
        String budgetLowerBoundString = elementPrivacyBudget.element("value").getTextTrim();
        Double budgetLowerBound = Double.valueOf(budgetLowerBoundString);
        return budgetLowerBound;
    }

    public static Integer getDefaultTypeSize() {
        Integer result;
        try {
            result = (Integer) xmlConfigure.getIndependentData("UserType", "default", "default").getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String getDatasetBasicPath() {
        Document document = xmlConfigure.getDocument();
        Element elemnet = (Element)document.selectNodes("//datasets/basicPath").get(0);
        return elemnet.getTextTrim();
    }

    public static String getDatasetFileName(String tagName) {
        Document document = xmlConfigure.getDocument();
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
        System.out.println(ConfigureUtils.projectPath);
    }
}
