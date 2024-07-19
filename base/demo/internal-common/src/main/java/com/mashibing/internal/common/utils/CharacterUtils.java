package com.mashibing.internal.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * @author: kavanLi-R7000
 * @create: 2024-07-01 09:54
 * To change this template use File | Settings | File and Code Templates.
 */

@Slf4j
public class CharacterUtils {
    /**
     * 对传入的字符串进行判断，对于中文字符，使用现有的拼音首字母大写规则，对于英文字符，直接转换为大写，对于数字字符，保持不变，并拼接上时间戳返回。
     *
     * 比如 付款FK123 -> FKFK12320240701095058
     *
     * @param input
     * @return
     */
    public static String generateExchangeNo(String input) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 格式化当前时间
        String timestamp = now.format(formatter);

        // 获取汉字拼音首字母并转换为大写
        StringBuilder result = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for (char ch : input.toCharArray()) {
            if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) { // 判断是否是汉字
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    if (pinyinArray != null) {
                        result.append(pinyinArray[0].charAt(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (Character.isLetter(ch)) { // 判断是否是字母
                result.append(Character.toUpperCase(ch));
            } else if (Character.isDigit(ch)) { // 判断是否是数字
                result.append(ch);
            }
        }

        // 拼接前缀和时间戳
        String paymentId = result.toString() + timestamp;

        return paymentId;
    }

}
