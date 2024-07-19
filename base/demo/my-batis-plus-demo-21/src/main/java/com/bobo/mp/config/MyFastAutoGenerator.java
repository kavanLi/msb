package com.bobo.mp.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 代码生成器
 */
public class MyFastAutoGenerator {
    public static void main(String[] args) {
        oracle();
        //mysql();
    }

    public static void oracle() {
        FastAutoGenerator.create("jdbc:oracle:thin:@192.168.30.52:1521:orcldb"
                        , "yunstdb", "uat_QAZ123")
                .globalConfig(builder -> {
                    builder.author("litg1") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\kavanLi-R7000\\Desktop\\TL\\mybatis-generator"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.allinpay.yunst.bm") // 设置父包名
                            .moduleName("yunstapi") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\kavanLi-R7000\\Desktop\\TL\\mybatis-generator")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("ORGANIZATION") // 设置需要生成的表名
                            .addTablePrefix("DYNA_AMS_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    public static void mysql() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mp?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true"
                        , "root", "123456")
                .globalConfig(builder -> {
                    builder.author("boge") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://MyBatisPlus"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.bobo.mp") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
