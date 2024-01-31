package com.mashibing.framework.beans.factory.xml;

import com.mashibing.framework.beans.BeanDefinition;
import com.mashibing.framework.beans.MutablePropertyValues;
import com.mashibing.framework.beans.PropertyValue;
import com.mashibing.framework.beans.factory.support.BeanDefinitionReader;
import com.mashibing.framework.beans.factory.support.BeanDefinitionRegistry;
import com.mashibing.framework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 该类是对XML文件进行解析的具体类
 * @author spikeCong
 * @date 2022/10/28
 **/
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    //声明注册表对象
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader() {
        registry = new SimpleBeanDefinitionRegistry();
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public void loadBeanDefinitions(String configLocation) throws Exception {

        //使用Dom4j解析XML
        SAXReader reader = new SAXReader();

        //获取指向配置文件的输入流
        InputStream is = XmlBeanDefinitionReader.class.getClassLoader().getResourceAsStream(configLocation);

        //文档对象
        Document document = reader.read(is);

        //获取根标签
        Element rootElement = document.getRootElement();

        //解析bean标签
        parseBean(rootElement);
    }

    private void parseBean(Element rootElement) {

        //获取所有的bean标签
        List<Element> elements = rootElement.elements();

        //遍历每个bean标签 获取id className属性以及property子标签
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String className = element.attributeValue("class");

            //封装数据到BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);

            //获取property
            List<Element> elementList = element.elements("property");

            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();

            for (Element property : elementList) {
                String name = property.attributeValue("name");
                String ref = property.attributeValue("ref");
                String value = property.attributeValue("value");
                PropertyValue propertyValue = new PropertyValue(name, ref, value);

                mutablePropertyValues.addPropertyValue(propertyValue);
            }

            //将mutablePropertyValues封装到 BeanDefinition
            beanDefinition.setPropertyValues(mutablePropertyValues);

            System.out.println(beanDefinition);

            //将beanDefinition注册到注册表
            registry.registerBeanDefinition(id,beanDefinition);
        }
    }

    public static void main(String[] args) throws Exception {

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader();

        reader.loadBeanDefinitions("bean.xml");
    }
}
