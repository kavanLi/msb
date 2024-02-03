package com.mashibing.jvm.c3_jmm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ObjectSizeAgent implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class <?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        return classfileBuffer; // 不对类字节码进行转换，直接返回原始字节码
    }
}