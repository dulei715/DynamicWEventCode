package scheme_test.test_tools;

import cn.edu.dll.configure.XMLConfigure;
import cn.edu.dll.configure.XMLConfigureUtils;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.pair.PurePair;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class ParameterConfigTest {

    @Test
    public void fun0() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream inputStream = BudgetDistribution.class.getClassLoader().getResourceAsStream("../classes/config/parameter_config.xml");
        Document document = reader.read(inputStream);
//        System.out.println(document);
        Element rootElement = document.getRootElement();
        System.out.println(rootElement.getName());
//        Attribute idAttr = rootElement.attribute("id");
//        System.out.println(idAttr.getName());
//        System.out.println(idAttr.getValue());
        MyPrint.showSplitLine("*", 150);
//        Iterator iterator = rootElement.elementIterator();
//        while (iterator.hasNext()) {
//            Element next = (Element)iterator.next();
//            System.out.println(next.getName());
//        }
//        List attributes = rootElement.attributes();

//        for (Object attribute : attributes) {
//            System.out.println(((Element)attribute).getName());
//        }
//        System.out.println(rootElement.getName());

//        List attributes = rootElement.attributes();
//        for (Object attribute : attributes) {
//            System.out.println(((Attribute)attribute).getName());
//        }

//        List elements = rootElement.element("independentVariables").elements("attribute");
//        for (Object element : elements) {
//            System.out.println(((Element)element).attribute("name").getValue());
//        }

        List<Node> nodeList = document.selectNodes("//attribute[@name='PrivacyBudget']");
//        System.out.println(nodeList.size());
        for (Node node : nodeList) {
            System.out.println(node.asXML());
        }


    }

    @Test
    public void fun1() {
        Element independentVariableElement = XMLConfigureUtils.getFirstLayerElementByTagName("config/IndependentVariable.xml");
        Iterator iterator = independentVariableElement.elementIterator();

        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }
    }

    @Test
    public void fun2() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        XMLConfigure xmlConfigure = new XMLConfigure("config/parameter_config.xml");
        PureTriple<String, Object, List<Object>> result = xmlConfigure.getIndependentData("UserType", "default", "default");
        System.out.println(result.getKey());
        System.out.println(result.getValue());
        MyPrint.showList(result.getTag(), "; ");
    }

    @Test
    public void fun3() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        XMLConfigure xmlConfigure = new XMLConfigure("config/parameter_config.xml");
        PurePair<String, Object> result = xmlConfigure.getDependentData("TimeCost");
        System.out.println(result.getKey());
        System.out.println(result.getValue());
    }

}
