package com.mashibing.internal.common.utils.easyExcel.setting;

/**
 * @author: kavanLi-R7000
 * @create: 2024-07-04 19:59
 * To change this template use File | Settings | File and Code Templates.
 */

import java.awt.*;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 水印配置类
 */
@Data
@Accessors(chain = true)
public class WaterMark {
    /**
     * 水印内容
     */
    private String content = "";

    /**
     * 画笔颜色. 格式为"#RRGGBB"，eg: "#C5CBCF"
     */
    private String color = "#C5CBCF";

    /**
     * 字体样式
     * java图片打水印中文乱码，显示一个方框解决方案：
     * Linux上需要安装中文字体，
     * JRE里面也要安装中文字体的，
     * 在$JAVA_HOME/jre/lib/fonts下新建一个fallback目录，复制程序用到的字体进去
     *
     * 解决Java应用在Linux下无法正常水印生僻字
     * 最简单的解决方案是：
     * 1）在$JAVA_HOME/jre/lib/fonts下新建一个fallback目录，让Java程序渲染生僻字时在fallback里面找。
     * 2）把windows/fonts/simsun.ttc拷贝到fallback去
     *
     * DejaVu Sans:
     *
     * 跨平台：DejaVu 字体是开源的，预安装在大多数 Linux 发行版上。
     * 包含大量 Unicode 字符，适用于各种语言。
     */
    private Font font = new Font("DejaVu Sans", Font.BOLD, 40);
    //private Font font = new Font("Microsoft YaHei", Font.BOLD, 40);

    /**
     * 水印宽度
     */
    private int width = 300;

    /**
     * 水印高度
     */
    private int height = 100;

    /**
     * 水平倾斜度
     */
    private double shearX = 0.1;

    /**
     * 垂直倾斜度
     */
    private double shearY = -0.26;

    /**
     * 字体的y轴位置
     */
    private int yAxis = 50;

}
