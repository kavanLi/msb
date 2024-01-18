/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : stock_subscription

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 11/11/2022 13:43:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock_do
-- ----------------------------
DROP TABLE IF EXISTS `stock_do`;
CREATE TABLE `stock_do`  (
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`symbol`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_do
-- ----------------------------
INSERT INTO `stock_do` VALUES ('AMD', 'AMD Yes!');
INSERT INTO `stock_do` VALUES ('AMZN', '亚马逊');
INSERT INTO `stock_do` VALUES ('APPL', '苹果');
INSERT INTO `stock_do` VALUES ('BABA', '阿里巴巴');
INSERT INTO `stock_do` VALUES ('GOOG', '谷歌');
INSERT INTO `stock_do` VALUES ('INTC', '英特尔');
INSERT INTO `stock_do` VALUES ('KO', '可口可乐');
INSERT INTO `stock_do` VALUES ('MSFT', '微软');
INSERT INTO `stock_do` VALUES ('NFLX', '奈飞');
INSERT INTO `stock_do` VALUES ('PYPL', '贝宝');
INSERT INTO `stock_do` VALUES ('SBUX', '星巴克');
INSERT INTO `stock_do` VALUES ('TSLA', '特斯拉');
INSERT INTO `stock_do` VALUES ('XIACY', '小米');

-- ----------------------------
-- Table structure for stock_subscription_do
-- ----------------------------
DROP TABLE IF EXISTS `stock_subscription_do`;
CREATE TABLE `stock_subscription_do`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_subscription_do
-- ----------------------------
INSERT INTO `stock_subscription_do` VALUES (10001, 'msb@qq.com', 'TSLA');
INSERT INTO `stock_subscription_do` VALUES (10002, 'msb@qq.com', 'AMZN');
INSERT INTO `stock_subscription_do` VALUES (10003, 'msb@qq.com', 'APPL');
INSERT INTO `stock_subscription_do` VALUES (10004, 'msb@qq.com', 'XIACY');

SET FOREIGN_KEY_CHECKS = 1;
