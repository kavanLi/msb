package com.mashibing.internalcommon.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 深度克隆 实现Serializable接口,如果类中存在组合形式的使用,那么每个类都要实现Serializable接口
 * @author: kavanLi
 * @create: 2019-11-01 16:43
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public final class ObjClonerUtils {
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    public static <T> T CloneObj(T obj) {
        T retobj=null;
        try {
            //写入流中
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            //从流中读取
            ObjectInputStream ios = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            retobj=(T) ios.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return retobj;
    }
    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
