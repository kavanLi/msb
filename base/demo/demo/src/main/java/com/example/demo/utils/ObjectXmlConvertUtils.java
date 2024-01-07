package com.example.demo.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

/**
 * java对象与xml相互转换
 *
 * @author caijiahua
 */
public class ObjectXmlConvertUtils {

    @SuppressWarnings("deprecation")
    public static String convertObj2Xml(Object obj) {
        Assert.notNull(obj);
        Class <?> clazz = obj.getClass();
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现

            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> T convertXmlStrToObject(Class <T> clazz, String xmlPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            String xmlStr = "";
            try {
                xmlStr = FileUtils.readFileToString(new File(xmlPath), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringReader sr = new StringReader(xmlStr);
            Object obj = unmarshaller.unmarshal(sr);
            return (T) obj;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
