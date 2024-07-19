package com.mashibing.internal.common.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 敏感字段解密工具
 *
 * @author gejunqing
 * @version 1.0
 * @date 2024/1/25
 */
@Slf4j
public class DecodeUtils {

    // 定义需要解密的字段列表
    private static final List<String> FIELD_NAMES_TO_DECRYPT = Stream.of("cerNum"
                    , "acctNum"
                    , "validDate"
                    , "cvv2"
                    , "bankCardNo"
                    , "legalPersonCerNum"
                    , "beneficiaryCerNum"
                    , "shareholderCerNum"
                    , "authPerCerNum"
            )
            .collect(Collectors.toList());

    public static String decryptFields(String jsonString, String hexKey) {
        try {
            // 将JSON字符串转换为JSON对象
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString);

            // 递归遍历要解密的字段列表
            decryptFieldsRecursively(rootNode, hexKey);

            // 遍历要解密的字段列表
            //for (String fieldName : FIELD_NAMES_TO_DECRYPT) {
            //    // 如果字段存在，则进行解密
            //    if (rootNode.path(fieldName).isMissingNode()) {
            //        continue;
            //    }
            //
            //    String encryptedFieldValue = rootNode.path(fieldName).asText();
            //    String decryptedFieldValue = DemoSM4Utils.decryptEcb(hexKey, encryptedFieldValue);
            //
            //    // 更新JSON对象中的解密后的字段值
            //    ((ObjectNode) rootNode).put(fieldName, decryptedFieldValue);
            //}

            // 返回更新后的JSON字符串
            return mapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            log.info("返回结果的敏感字段解密失败：{}", e.getLocalizedMessage());
            return jsonString;
        }
    }

    private static void decryptFieldsRecursively(JsonNode node, String hexKey) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.fieldNames().forEachRemaining(fieldName -> {
                if (FIELD_NAMES_TO_DECRYPT.contains(fieldName)) {
                    JsonNode fieldNode = objectNode.path(fieldName);
                    if (!fieldNode.isMissingNode()) {
                        String decryptedValue = null;
                        try {
                            //decryptedValue = DemoSM4Utils.decryptEcb(hexKey, fieldNode.asText());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        objectNode.put(fieldName, decryptedValue);
                    }
                } else {
                    decryptFieldsRecursively(objectNode.get(fieldName), hexKey);
                }
            });
        } else if (node.isArray()) {
            for (JsonNode jsonNode : node) {
                decryptFieldsRecursively(jsonNode, hexKey);
            }
        }
    }

}
