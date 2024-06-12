package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.configure.XMLConfigure;
import org.dom4j.Document;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;

public class ConfigureUtils {
    private static XMLConfigure xmlConfigure = new XMLConfigure("config/parameter_config.xml");
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

    public static Integer getDefaultTypeSize() {
        Integer result;
        try {
            result = (Integer) xmlConfigure.getIndependentData("UserType", "default", "default").getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        Double privacyBudgetUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        System.out.println(privacyBudgetUpperBound);
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        System.out.println(windowSizeLowerBound);
    }
}
