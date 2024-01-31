package com.mashibing.framework.context.support;

import com.mashibing.framework.beans.BeanDefinition;
import com.mashibing.framework.beans.MutablePropertyValues;
import com.mashibing.framework.beans.PropertyValue;
import com.mashibing.framework.beans.factory.support.BeanDefinitionRegistry;
import com.mashibing.framework.beans.factory.xml.XmlBeanDefinitionReader;
import com.mashibing.framework.utils.SpringUtils;

import java.lang.reflect.Method;

/**
 * IOC容器具体的子实现类,加载XML格式的配置文件
 * @author spikeCong
 * @date 2022/10/28
 **/
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

    public ClassPathXmlApplicationContext(String configLocation) {

        this.configLocation = configLocation;

        //构建解析器对象,指定具体解析类为XML
        this.beanDefinitionReader = new XmlBeanDefinitionReader();

        this.refresh();
    }

    //根据bean的对象名称获取bean对象
    @Override
    public Object getBean(String name) throws Exception {

        //判断对象容器中是否包含指定名称的bean对象,有就返回,没有则创建
        Object obj = singletonObjects.get(name);
        if(obj != null){
            return obj;
        }

        //自行创建,获取BeanDefinition对象
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        BeanDefinition beanDefinition = registry.getBeanDefinition(name);

        //通过反射创建对象
        String className = beanDefinition.getClassName();
        Class clazz = Class.forName(className);
        Object beanObj = clazz.newInstance();  //实例化

        //需要进行依赖注入
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            //获取property标签的属性
            String pName = propertyValue.getName();
            String pRef = propertyValue.getRef();
            String pValue = propertyValue.getValue();

            if(pRef != null && !"".equals(pRef)){
                //获取依赖的bean对象
                Object bean = getBean(pRef);
                //setCourseDao()  set + courseDao
                String methodName = SpringUtils.getSetterMethod(pName);

                //获取所有方法对象
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if(methodName.equals(method.getName())){
                        //执行该方法
                        method.invoke(beanObj,bean);
                    }
                }
            }

            if(pValue != null && !"".equals(pValue)){
                String methodName = SpringUtils.getSetterMethod(pName);
                //获取method
                Method method = clazz.getMethod(methodName, String.class);
                method.invoke(beanObj,pValue);
            }
        }

        //在返回beanObj之前,需要将对象存储到Map容器中
        this.singletonObjects.put(name,beanObj);

        return beanObj;
    }

    @Override
    public <T> T getBean(String name, Class<? extends T> clazz) {

        Object bean = null;

        try {
            bean = getBean(name);
            if(bean == null){
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clazz.cast(bean);
    }
}
