package com.example.demo.designpattern.proxy.asm.gpt;

import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-01 10:39
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestASM {

    /**
     Cglib的底层原理是ASM:

     Cglib使用ASM字节码生成框架，动态生成目标类的子类，并在子类中插入自定义的逻辑。

     JDK动态代理的底层原理是Java反射:

     JDK动态代理使用Java反射机制，在运行时动态生成代理类，并使用InvocationHandler接口来实现代理逻辑。

     Cglib和JDK动态代理的比较:

     特性	Cglib	JDK动态代理
     底层原理	ASM	Java反射
     性能	较高	较低
     侵入性	强	弱
     适用范围	广泛	仅限于实现了接口的类

     如果需要对目标类进行更复杂的控制，可以选择Cglib。
     如果只需要简单的代理功能，可以选择JDK动态代理。

     * 设计模式与ASM的关系
     * ASM是一个Java字节码操作和分析框架，可以直接操作Java字节码，无需编译/反编译。ASM可以用于实现设计模式，例如代理模式、工厂模式、装饰模式等。
     *
     * ASM实现设计模式的原理:
     *
     * ASM可以修改现有的字节码，插入自定义的逻辑。
     * ASM可以生成新的字节码，实现新的功能。
     * ASM实现设计模式的优点:
     *
     * 灵活: 可以根据需要动态修改或生成字节码，从而使代码更加灵活。
     * 高效: ASM可以直接操作字节码，无需解释，因此效率较高。
     * ASM实现设计模式的缺点:
     *
     * 复杂: ASM的API比较复杂，使用起来有一定的难度。
     * 侵入性: ASM需要修改目标类的字节码，因此具有一定的侵入性。
     * 案例代码:
     *
     * 示例：使用ASM实现代理模式
     *
     * 假设我们有一个目标类Target，需要对该类进行代理。可以使用ASM动态生成一个代理类，如下代码所示：
     */
    public static void main(String[] args) throws Exception {
        // 创建ClassWriter对象，用于生成新的字节码
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // 定义代理类的名称和访问权限
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Proxy", null, "java/lang/Object", new String[] {"Target"});

        // 生成代理类的方法
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "doSomething", "()V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("代理类执行doSomething方法");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "Target", "doSomething", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        // 生成代理类的字节码
        byte[] bytes = cw.toByteArray();

        // 将字节码加载到JVM中
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if ("Proxy".equals(name)) {
                    return defineClass(name, bytes, 0, bytes.length);
                }
                return super.loadClass(name);
            }
        };

        // 创建代理类对象
        Target proxy = (Target) classLoader.loadClass("Proxy").newInstance();

        // 调用代理类的方法
        proxy.doSomething();
    }
}

// 目标类
class Target {
    public void doSomething() {
        System.out.println("目标类执行doSomething方法");
    }
}

